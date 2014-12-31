package com.transactions.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.accounts.pkg.Servlet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


@WebServlet("/CancelOrder")
public class CancelOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public CancelOrder() {
        super();
        // TODO Auto-generated constructor stub

    }

    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hi");
		String pressed = request.getParameter("confirm");
		System.out.println("pressed = "+pressed);
		if(pressed == null)
		{
			response.sendRedirect("cart.jsp");
		}
		else
		{
			HttpSession session = request.getSession();
			String cust_id = (String) session.getAttribute("cust_id");
			String cancel_id = (String) session.getAttribute("cancel_id");
			
			if(cancel_id!=null) 
			{
				
				String msg = " ";
				int qty = 0;
				int prod_id = Integer.parseInt(cancel_id);
				String order_id = checkOrder(cust_id,cancel_id);
				if(order_id==null)
				{
					msg = "<html><body><script>alert('Order already processed. Cannot Cancel this item!');window.location = 'cart.jsp';</script></body></html>";
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println(msg);
				}
				else
				{
					qty = checkQty(order_id);
					
					try
					{
						String query = "Update `orders`.`orders` SET status = 'Cancelled' WHERE order_id='"+order_id+"';";
						String query2 = "Update `orders`.`products` SET `qty`='qty'+'"+qty+"' where 'pid'='"+prod_id+"';";
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
						PreparedStatement statement = (PreparedStatement) con.prepareStatement(query);
						PreparedStatement preparedStmt2 = (PreparedStatement) con.prepareStatement(query2);
						statement.execute();
						preparedStmt2.execute();
						statement.close();
						preparedStmt2.close();
						con.close();
						response.sendRedirect("cart.jsp");
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	private int checkQty(String order_id) {
		int qty = 0;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
			PreparedStatement statement = (PreparedStatement) con.prepareStatement("select * from orders where order_id='"+order_id+"';");
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				qty = result.getInt("qty");
				return qty;
			
			}	
			
			con.close();
			statement.close();
			
		}	catch(Exception e)	{
			e.printStackTrace();
		}
		return 0;
	}

	private String checkOrder(String cust_id, String cpid) {
		String order_id = " ";
		String status = " ";
		java.sql.Date order_date;
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime ( today );
		int daysToDecrement = -3;
		cal.add(Calendar.DATE, daysToDecrement);
		today = cal.getTime();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
			PreparedStatement statement = (PreparedStatement) con.prepareStatement("select * from orders where cust_id='"+cust_id+"' AND prod_id='"+cpid+"';");
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				order_id = result.getString("order_id");
				order_date = result.getDate("order_date");
				status =result.getString("status");
				//check date validity
				if(checkDate(today,order_date)&&status.equals("PENDING"))
				{
					return order_id;
				}
				return null;
			
			}	
			
			con.close();
			statement.close();
			
		}	catch(Exception e)	{
			e.printStackTrace();
		}
		return null;
	}

	private boolean checkDate(Date today, java.sql.Date order_date) {
		Date orderDate;
		try {
			if(today.before(order_date)||today.equals(order_date))
			{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
