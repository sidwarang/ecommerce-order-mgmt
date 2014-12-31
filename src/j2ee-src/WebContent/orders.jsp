<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*, javax.servlet.*"%>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Checkout</title>
<link rel='stylesheet' href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>
<link rel="stylesheet" href="/OrderManagementApp/style.css" media="screen" type="text/css" />
<script type="text/javascript">
document.getElementById('qty').onchange = getCost;

function getCost(){
  var qty =  document.getElementById('qty').innerHTML;
  
}
</script>
</head>
<body>
<%if(session.getAttribute("pid")==null)
	response.sendRedirect("cart.jsp");%>
	<p>Welcome, <%=session.getAttribute("uname")%> Proceed to Check-out you item: <%=session.getAttribute("pid")%></p>
	<div class="login-card">
  	<form name='billing' action='CheckoutServlet' method="post">
  	<h1>Product Details</h1><br>
  	<table style="width:100%">
  	<%
  	List<String> msg = new ArrayList<String>();
  	//boolean first = false;
  	if(session.getAttribute("clicked")!=null){
  	boolean first = false;
  	boolean clicked = (boolean) session.getAttribute("clicked");
	String em = request.getParameter("email");
	String f = request.getParameter("first");
	String l = request.getParameter("last");
	String ph = request.getParameter("num");
	String a = request.getParameter("add");
	String c = request.getParameter("city");
	String st = request.getParameter("state");
	String z = request.getParameter("zip");
	String n = request.getParameter("country");
	String ca = request.getParameter("card");
	String m = request.getParameter("MM");
	String ye = request.getParameter("YYYY");
	String co = request.getParameter("code");
	//msg.add("Error: please complete the following");
	if(clicked)
	{
		if(em == null)
		{msg.add("enter email");}
		else
		{if(em.indexOf("@")==-1||em.indexOf("@")==0||em.indexOf(".")==-1||em.indexOf(".")==0)
			msg.add("enter valid email"); 
		}
		if(f == null)
		{msg.add("enter first name");}
		if(l == null)
		{msg.add("enter last name");}
		if(ph == null)
		{msg.add("enter valid contact number");}
		if(a == null)
		{msg.add("enter address");}
		if(c == null)
		{msg.add("enter city");}
		if(st == null)
		{msg.add("enter state");}
		if(z == null||z.length()!=5)
		{msg.add("enter valid zip");}
		if(n == null)
		{msg.add("enter country");}
		if(ca == null||ca.length()!=12)
		{msg.add("enter valid card number");}
		if(m == null||ye == null||m.length()!=2||ye.length()!=4)
		{msg.add("enter valid expiry date");}
		else{int mon = Integer.parseInt(m);int yr = Integer.parseInt(ye);
			 int year = Calendar.getInstance().get(Calendar.YEAR);
			 int month = Calendar.getInstance().get(Calendar.MONTH);
			if(mon<1||mon>12||yr<year)
				{msg.add("enter valid expiry date");}
			else{if(mon<month)msg.add("enter valid expiry date");}}
		if(co == null||co.length()!=3)
		{msg.add("enter valid code");}
        first = false;
        session.setAttribute("validated", "invalid");
        System.out.println("1"+msg);
	}
	else
	{session.setAttribute("validated", "valid");
		first = true;
		System.out.println("2"+msg);} }
	else{
	System.out.println("3"+msg);}%>
  	<% 
  	String img = (String) session.getAttribute("img");
	String name = (String) session.getAttribute("prod_name");
	String price = (String) session.getAttribute("price");
	List<String> details = (List<String>)session.getAttribute("details");
	StringBuffer output = new StringBuffer("<tr><td align='center' colspan=2>"+name+"</td></tr>");
	output.append("<tr><td align='center' colspan=2><img name='item' src='"+img+"' alt='item' style='width:180px;height:260px'></td></tr>");
	output.append("<tr><td align='center' colspan=2>Details:</td></tr>");
	for(String s : details)
	{
		if(!s.contains("null"))
		{
			//System.out.println(s);
			output.append(s);
		//out.println(s);
		}
			
	}
	output.append("<tr><td>price</td><td>: "+price+"</td></tr>");
	//out.println("price: "+price);
	out.print(output.toString());
	 %>
  	<tr><td>Quantity</td><td align='right'><input type="text" name="qty" placeholder="1" maxlength=3></td></tr>
  	</table>
  	
  	<h1>Billing Information</h1><br>
  	<% if(msg.size()>1){
  		out.println("Error: please complete the following</br><ul>");
  	for(String ou : msg) {
  	out.println("<li>"+ou+"</li>");}
  	out.print("</ul>");
  	} %>
    <input type="text" name="email" value='<%=session.getAttribute("email")%>'>
    <input type="text" name="first" placeholder="First Name">
    <input type="text" name="last" placeholder="Last Name">
    <input type="text" name="num" placeholder="Contact number">
    <input type="text" name="add" placeholder="Address">
    <input type="text" name="city" placeholder="City">
    <input type="text" name="state" placeholder="State">
    <input type="text" name="zip" placeholder="zip-code">
    <input type="text" name="country" placeholder="Country">
    <h1>Shipping Options</h1><br>
    <table width='100%'><tr><td>
    <input type="radio" name="shipping" value="standard" checked>Standard ($0)<br></td><td><td>
    <input type="radio" name="shipping" value="standard">Express ($10)*<br></td></tr></table>
    <table width='100%'><tr><td align='center'><label style='color:red;size:8px'>*: currently unavailable</label></td></tr>
    <tr><td align='center'><p>expected delivery date : </p></td></tr><tr><td align='center'><p><%Date today = new Date(); Date delivery = new Date(today.getTime() + (1000 * 60 * 60 * 72)); out.print( "<h5 name='delivery' >" +delivery.toString()+"</h5>");%></p></td></tr></table>
    <h1>Finalize Order</h1><br>
    <table width='100%'>
    <tr><th align='left'>Items</th><th>Quantity</th><th align='right'>Cost</th></tr>
    <tr><td align='left'><%=name %></td><td align='center'>1</td><td align='right'><%=price %>x 1</td></tr>
    <%String p = price.substring(1);
      double x = Double.parseDouble(p);
      p=request.getParameter("qty");
      if(p!=null&&p!=""&&p!=" ")
      {
    	  int y = Integer.parseInt(p);
          x=x*y;
      }
      
      p = "$"+x;
    %>
    <tr><td></td><td>SUBTOTAL:</td><td align='right'><%=p %></td></tr>
    <tr><td></td><td>TAX:</td><td align='right'>$0</td></tr>
    <tr><td></td><td>TOTAL:</td><td align='right'><%=p %></td></tr>
    </table>
    <h1>Payment Info</h1><br>
    <input type="text" name="card" placeholder="Card Number">
    <input type="text" name="MM" placeholder="MM (expiration month)">
    <input type="text" name="YYYY" placeholder="YYYY (expiration year)">
    <input type="text" name="code" placeholder="CCV">
    <input type="submit" name="proceed" class="login login-submit" value="Complete Order"></form>
    <form name='cancelOrd' action='CancelTransaction' method="post">
    <input type="submit" name="cancelOrd" class="login cancel-submit" value="Cancel Order">
  	</form>
</div>
</body>
</html>






