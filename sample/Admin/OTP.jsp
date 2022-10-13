
<!Doctype html>
<html>
<head>
</head>
<body>

<h2>Otp Authentication</h2>
<% 
try{
	System.out.println(request.getParameter("j_username"));
	System.out.println(request.getParameter("j_password"));
	String name = request.getUserPrincipal().getName();
%>


<%}
catch(Exception e){
	System.out.println("[+] end of first call");
	System.out.println(request.getParameter("j_username"));
	System.out.println(request.getParameter("j_password"));
	
%>
	<form action="j_security_check" method="POST">
		<label>Enter the OTP shown in your google authenticator app</label>
		<input id="otp" type="number" name="j_username"/><br />
		<input id="otp" type="hidden" name="j_password" value="<%= request.getParameter("j_username")%>"/><br />
		<input type="submit" value="submit"/>
	</form>
	<%
}
%>




</body>
</html>