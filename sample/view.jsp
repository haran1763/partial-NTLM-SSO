<%@ page import = "java.util.*,java.sql.Statement,java.sql.DriverManager,java.sql.Connection,java.sql.ResultSet,java.sql.SQLException,java.text.SimpleDateFormat,java.text.SimpleDateFormat"%>

<!Doctype html>
<html>
<head>
</head>
<body>

<h1> Your appointments are :

<%
	try{
	Statement stmt = null;
      ResultSet rs = null;
	  ResultSet rs1 = null;
	String jdbcURL = "jdbc:postgresql://localhost:5432/managementDB";
      String user = "postgres";
      String pwd = "1763";
	Date date =null;
	String hname=null,dname=null;
	int did=0;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
	Connection conn = null;
	conn = DriverManager.getConnection(jdbcURL,user,pwd);
	stmt = conn.createStatement();

	String email = request.getUserPrincipal().getName();

	String query = String.format("select patientid from patient where email = '%s';",email);
	rs = stmt.executeQuery(query);
	if(rs.next()){
		query = String.format("select d_idno,starttime from appointment where pidno = '%d';", rs.getInt(1));
	}
	rs1 = stmt.executeQuery(query);
	while(rs1.next()){
		did = rs1.getInt(1);
	    date = new Date(rs1.getLong(2));
	
    query = String.format("select name,hospitalid from doctors where doctorid = '%d';",did);
	rs = stmt.executeQuery(query);
	if(rs.next()){
		dname = rs.getString(1);
		query = String.format("select name from hospitals where hospitalid = '%d';",rs.getInt(2));
	}
	rs = stmt.executeQuery(query);
	if(rs.next()){
                hname = rs.getString(1);
      }
%>
	<h3>Appointment fixed with <%= dname %> in <%= hname %> hospital on <%= sdf.format(date) %></h3>

<%
	}
}
catch(Exception e){
	e.printStackTrace();
}
%>
 
<form action="./logout">
<input type="submit" value="Log Out" />
</form>


</body>
</html>