<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Log-in</title>
  <link rel='stylesheet' href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>
  <link rel="stylesheet" href="/OrderManagementApp/style.css" media="screen" type="text/css" />
</head>
<body>
<%
if(session.getAttribute("cust_id")==null)
{
	//System.out.println(1);
	if(session.getAttribute("pid")==null)
	{
	String pid = request.getParameter("pid");
	//System.out.println("2"+pid);	
	session.setAttribute( "pid", pid );	
	}
}
else
{
	//System.out.println(4);
	if(session.getAttribute("pid")==null)
	{	
		if(request.getParameter("pid")==null)
		{
			response.sendRedirect("cart.jsp");
			//System.out.println(5);
		}
		else
		{
			String pid = request.getParameter("pid");
			session.setAttribute( "pid", pid );
		}		
	}
	else
	{
		//System.out.println(6);
		response.sendRedirect("orders.jsp");
	}
}
%>
<div class="login-card">
    <h1>Log-in</h1><br>
  <form name='login' action='OrderServlet' method="post">
    <input type="text" name="user" placeholder="Username">
    <input type="password" name="pass" placeholder="Password">
    <input type="submit" name="login" class="login login-submit" value="login">
  </form>

  <div class="login-help">
    <a href="register.jsp">Register</a>
  </div>
</div>
</body>
</html>