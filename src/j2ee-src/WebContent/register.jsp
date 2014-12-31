<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Register</title>
  <link rel='stylesheet' href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>
  <link rel="stylesheet" href="/OrderManagementApp/style.css" media="screen" type="text/css" />    
</head>
<body>
<div class="login-card">
    <h1>Register</h1><br>
  <form name='login' action='OrderServlet' method="post">
    <input type="text" name="user" placeholder="Username">
    <input type="text" name="email" placeholder="Email">
    <input type="password" name="pass" placeholder="Password">
    <input type="password" name="cpass" placeholder="Confirm Password">
    <input type="submit" name="login" class="login login-submit" value="Register">
  </form>
</div>
</body>
</html>