<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.mysql.jdbc.*, java.sql.*, java.text.*, com.mysql.jdbc.Connection, com.mysql.jdbc.PreparedStatement "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel='stylesheet' href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>
<link rel="stylesheet" href="/OrderManagementApp/style.css" media="screen" type="text/css" />
</head>
<body>
	<div class="login-card">
	<%String cancel_id = request.getParameter("order_id");
	session.setAttribute("cancel_id", cancel_id);
	System.out.println("cancel id:"+cancel_id);
	String cancel_num = " ";
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/orders","root","");
	PreparedStatement statement = (PreparedStatement) con.prepareStatement("select * from orders where order_id='"+cancel_id+"';");
	ResultSet result = statement.executeQuery();
	if(result.next())
	{
		cancel_num = result.getString("order_no");
	}
	%>
	<form name='confirmation' action='CancelOrder' method='post'>
	<table>
	<tr><td align='center'><p>Are you sure you want to cancel this order?</p></td></tr>
	<tr><td align='center'><p>order no:</p></td></tr>
	<tr><th align='center'><p><%=cancel_num %></p></th></tr>
	<tr><td><input type="submit" name="confirm" class="login cancel-submit" value="Confirm"></td></tr>
	<tr><td><input type="submit" name="return" class="login login-submit" value="Return"></td></tr>
	</table>
	</form>
	</div>
</body>
</html>