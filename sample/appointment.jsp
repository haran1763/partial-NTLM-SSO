<%@ page import = "java.util.*"%>

<!Doctype html>
<html>
<head>
</head>
<body>
<h1>Make your wish....</h1>

<form action="/sample/book.jsp" method="POST">
<input type="submit" value="Book Appointment" />
</form>



<form id='view' action="/sample/view.jsp" method="POST">
<input type="submit" value="View Appointment" />
</form>

<form action="./logout">
<input type="submit" value="Log Out" />
</form>

</body>
</html>