package com.transactions.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.DBObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.pojos.pkg.Item;
import com.utilities.pkg.Driver;


@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public CheckoutServlet() {
        super();
    }

	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("clicked", true);
		String valid = (String) session.getAttribute("validated");
		
		if(valid!=null||valid =="valid")
		{
			String prod_id = (String) session.getAttribute("pid");
			String msg = " ";
			String qty = request.getParameter("qty");
			
			if(qty==null||qty==" "||qty=="")
			{
				qty = "1";
			}
			int req_qty = 1;
			int inv_qty = 0;
			String price = getPrice(prod_id,req_qty);
			int pid = Integer.parseInt(prod_id);
			if(checkMongo(prod_id))
			{
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
					PreparedStatement statement = (PreparedStatement) con.prepareStatement("select * from products where pid='"+prod_id+"';");
					ResultSet result = statement.executeQuery();
					if(result.next())
					{
						inv_qty = Integer.parseInt(result.getString("qty"));
					
					}	
					
					con.close();
					statement.close();
					
				}	catch(Exception e)	{
					e.printStackTrace();
				}
				
				if(inv_qty >= req_qty)
				{
					inv_qty=inv_qty-req_qty;
					
					String email = request.getParameter("email");
					String first = request.getParameter("first");
					String last = request.getParameter("last");
					String num = request.getParameter("num");
					String add = request.getParameter("add");
					String city = request.getParameter("city");
					String state = request.getParameter("state");
					String zip = request.getParameter("zip");
					String country = request.getParameter("country");
					String card = request.getParameter("card");
					String month = request.getParameter("MM");
					String year = request.getParameter("YYYY");
					String code = request.getParameter("code");
					
					
					String cust_id = (String) session.getAttribute("cust_id");
					String order_no = getOrderId();
					try{
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
						java.util.Date date = new java.util.Date();
						java.sql.Date dateDB = new java.sql.Date(date.getTime());
						
						String query = "INSERT INTO `orders`.`orders` (`order_date`,`cust_id`,`prod_id`,`price`,`email`,`fname`,`lname`,`contact`,`address`,`zip`,`city`,`state`,`country`,`card`,`month`,`year`,`code`,`qty`,order_no) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
						String query2 = "Update orders.products SET qty= ? where pid=?;";
					      PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
					      PreparedStatement preparedStmt2 = (PreparedStatement) con.prepareStatement(query2);
					      preparedStmt.setDate (1, dateDB);
					      preparedStmt.setString (2, cust_id);
					      preparedStmt.setString (3, prod_id);
					      preparedStmt.setString (4, price);
					      preparedStmt.setString (5, email);
					      preparedStmt.setString (6, first);
					      preparedStmt.setString (7, last);
					      preparedStmt.setString (8, num);
					      preparedStmt.setString (9, add);
					      preparedStmt.setString (10, zip);
					      preparedStmt.setString (11, city);
					      preparedStmt.setString (12, state);
					      preparedStmt.setString (13, country);
					      preparedStmt.setString (14, card);
					      preparedStmt.setString (15, month);
					      preparedStmt.setString (16, year);
					      preparedStmt.setString (17, code);
					      preparedStmt.setInt (18, req_qty);
					      preparedStmt.setString (19, order_no);
					      
					      preparedStmt2.setInt(1,inv_qty);
					      preparedStmt2.setInt(2,pid);
					      preparedStmt.execute();
					      preparedStmt2.execute();
					       
					      con.close();
					      preparedStmt.close();
					      preparedStmt2.close();
					      
					      msg = "<html><body><script>alert('Order Processed"+"\\n"+"order no: "+order_no+"');window.location = 'cart.jsp';</script></body></html>";				      
						  response.setContentType("text/html");
						  PrintWriter out = response.getWriter();
						  out.println(msg);
						  session.setAttribute("pid", null);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					msg = "<html><body><script>alert('Requested number of Items Not in Inventory!');window.location = 'cart.jsp';</script></body></html>";
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println(msg);			
				}
			}
			else
			{
				msg = "<html><body><script>alert('Requested Item is invalid!');window.location = 'index.jsp';</script></body></html>";
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(msg);
			}
		}
		else{
			response.sendRedirect("orders.jsp");
		}
	}

	
	private String getPrice(String prod_id, int req_qty) {
		int prod = Integer.parseInt(prod_id);
		int qty = req_qty;
		Driver drv = new Driver();
		Item itm = drv.getItem(prod);
		String price = itm.getPrice();
		double tmp = Double.parseDouble(price.substring(1));
		tmp = tmp*qty;
		price = String.valueOf(tmp);
		return "$"+price;
	}


	private String getOrderId() {
		SecureRandom random = new SecureRandom();
		String oid = " ";
		String noid = new BigInteger(50, random).toString(32);		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");		
			PreparedStatement statement = (PreparedStatement) con.prepareStatement("select * from orders");
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				oid = result.getString("qty");
				if(oid==noid)
				{
					return getOrderId();
				}
				return noid;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null; 
	}


	private boolean checkMongo(String prod_id) {
		int prod = Integer.parseInt(prod_id);
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Driver dvr = new Driver();
		DBObject dobj = dvr.connectJongo(prod);
		System.out.println(dobj);
		String sdate = (String) dobj.get("sdate");
		String edate = (String) dobj.get("edate");
		Date startDate;
		Date endDate;
		try {
			startDate = df.parse(sdate);
			endDate = df.parse(edate);
			if(edate==null||edate==""||edate==" ")
			{
				if(dt.after(startDate)||dt.equals(startDate))
				{
					return true;
				}	
			}
			else
			{
			if((dt.after(startDate)||dt.equals(startDate))&&(dt.before(endDate)||dt.equals(endDate)))
			{
				return true;
			}	
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		
		return false;
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
