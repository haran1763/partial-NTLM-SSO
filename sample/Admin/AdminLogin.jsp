<!Doctype html>
<html>
<head>
</head>
<body>

<h2>Login page</h2>

<form action="./admin.jsp" method="POST">
	<label>Enter the email</label>
	<input type="text" name="username" /><br />
	<label>Enter the password</label>
	<input type="password" name="password" /><br />
	<input type="hidden" name="tname" id="demo" />
	<input type="submit" value="get OTP"/>
</form>

<script>
	document.getElementById("demo").value = window.location.pathname;
	// const reloadOnce =()=>{
    //     window.location.hash = "reload";
    // }
    // window.onload = reloadOnce();
</script>
</body>
</html>