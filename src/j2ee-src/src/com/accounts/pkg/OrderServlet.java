package com.accounts.pkg;
import com.crypto.pkg.*;
import com.pojos.pkg.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.Enumeration;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
/**
 * Servlet implementation class OrderServlet
 */

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
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
		SHAEncryption crypto = new SHAEncryption();
		String type = request.getParameter("login");
		String uname = request.getParameter("user");
		String pas = request.getParameter("pass");
		String pass = " ";
		String email = " ";
		String cid= " ";
		HttpSession session = request.getSession();
		String msg = " ";
		String pid = (String) session.getAttribute("pid");
		/** Login Section **/
		
		if(type.equals("login"))
		{			
		try {
			pass = crypto.encode(pas);
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
			PreparedStatement statement = (PreparedStatement) con.prepareStatement("select * from customers where cust_name='"+uname+"' and cust_pass='"+pass+"'");
			ResultSet result = statement.executeQuery();
			
		
			if(result.next())
			{
				email = result.getString("cust_email");
				cid = result.getString("custid");
				session.setAttribute("uname", uname);
				session.setAttribute("email", email);
				session.setAttribute("cust_id", cid);
				if(pid!=null)
				{
					System.out.println("orders.jsp");
					response.sendRedirect("orders.jsp");
					getDetails(session,pid);
				}
				else
				{
					System.out.println("cart.jsp");
					response.sendRedirect("cart.jsp");
				}
							
			}	
			else
			{
				session.setAttribute("uname", null);
			
				msg = "<html><body><script>alert('Incorrect credentials!');window.location = 'index.jsp';</script></body></html>";
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(msg);			
			}
			result.close();
			statement.close();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		}
		
		/** Registration section **/
		
		else if(type.equals("Register"))
		{
			String cpass = request.getParameter("cpass");
			email = request.getParameter("email");
			if(pas.equals(cpass))
			{
			try
		    {
				pass = crypto.encode(pas);
		      // create a mysql database connection
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
						     
		      // create a sql date object so we can use it in our INSERT statement
		      
		      // the mysql insert statement
				String query = "INSERT INTO `orders`.`customers` (`cust_name`,`cust_pass`,`cust_email`) VALUES (?,?,?);";
		 
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
		      preparedStmt.setString (1, uname);
		      preparedStmt.setString (2, pass);
		      preparedStmt.setString (3, email);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
		       
		      con.close();
		      preparedStmt.close();
		      msg = "<html><body><script>alert('Success! Please Login');window.location = 'index.jsp';</script></body></html>";
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(msg);
		      
		    }
		    catch (Exception e)
		    {
		    	e.printStackTrace();
		    }
		}
			else
			{
				session.setAttribute("uname", null);
				session.setAttribute("email", null);
				msg = "<html><body><script>alert('Password Mismatch!');window.location = 'register.jsp';</script></body></html>";
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(msg);
			}
		}
		else
		{
			
		}
		}

	private void getDetails(HttpSession session, String pid) {
		int prod_id = Integer.parseInt(pid);
		com.utilities.pkg.Driver drv = new com.utilities.pkg.Driver();
		Item itm = drv.getItem(prod_id);
		String name = itm.getName();
		String price = itm.getPrice();
		String img = itm.getImage();
		List<String> details = itm.toList();
		session.setAttribute("prod_name", name);
		session.setAttribute("price", price);
		session.setAttribute("details", details);
		session.setAttribute("img", img);
	    
	}

		
	}

