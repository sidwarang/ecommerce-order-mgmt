<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.mysql.jdbc.*, java.sql.*, java.text.*, com.mysql.jdbc.Connection, com.mysql.jdbc.PreparedStatement "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
<link rel='stylesheet' href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>
<link rel="stylesheet" href="/OrderManagementApp/style.css" media="screen" type="text/css" />
</head>
<body>
	<div class="login-card">
    <h1>Shopping Cart</h1><form name='LogOut' action='LogOut' method='post'><input type="submit" name="logout" class="login login-submit" value="logout"></form><br>
  <form name='ShoppingCart' >
    <%
    String cust_id = (String) session.getAttribute("cust_id");
    /*String order_date = " ";
	String price = " ";
	String qty = " ";
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");*/
	String prod_id = " ";
	String prod_name = " ";
	String status = " ";
	String order_id = " ";
	List<String> output = new ArrayList<String>();
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
	PreparedStatement statement = (PreparedStatement) con.prepareStatement("select * from orders where cust_id='"+cust_id+"';");
	ResultSet result = statement.executeQuery();
	int j = 0;
	while(result.next())
	{
		order_id = result.getString("order_id");
		prod_id = result.getString("prod_id");
		status = result.getString("status");
		PreparedStatement statement2 = (PreparedStatement) con.prepareStatement("select * from products where pid='"+prod_id+"';");
		ResultSet result2 = statement2.executeQuery();
		if(result2.next())
		{
			prod_name = result2.getString("prod_name");
			output.add("<tr><td >"+prod_name+"</td><td width='100%' align='center'>"+status+"</td><td><form name='button' action='cancel-confirm.jsp' method='get'><input type='hidden' name='order_id' value='"+order_id+"'><input type='submit' name='cancel' class='login cancel-submit' value='Cancel'></form></td></tr>");
		}		
	}		
	con.close();
	statement.close();
    %>
    <table>
    <tr><th>Product</th><th>Status</th><th>Action</th>
    <%
    for(int i =0;i<output.size();i++)
    {
    	out.println(output.get(i));
    }
    %>
    </table>
  </form>
</div>
</body>
</html>