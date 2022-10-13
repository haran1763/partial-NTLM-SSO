import java.sql.Statement;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.*;



public class Doctors extends HttpServlet{
    public void service(HttpServletRequest req,HttpServletResponse res) {
    System.out.println("[+] inside doctor get");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
    Date date,date1;
    ResultSet rs = null;
    Statement stmt = null;

    try {
        
                dbConnection db = new dbConnection();
                Connection conn = db.dbConnect();
                String name = req.getUserPrincipal().getName();
                System.out.println(req.isUserInRole("admin"));
                System.out.println(name);
                PrintWriter oWriter = res.getWriter();
                System.out.println("Inside Doctors module");
                stmt = conn.createStatement();

                oWriter.println("<html><body>");
                
                String str = String.format("select doctorid from doctor where email = '%s';",name);
                rs = stmt.executeQuery(str);
                if(rs.next()){
                str = String.format("select starttime,endtime from appointment where d_idno = %d;",rs.getInt(1));
                }
                System.out.println(str);
                rs = stmt.executeQuery(str);
                int i=1;
                if(rs.next()){
                    oWriter.println("<h2>The appointments you have are :</h2>");
                }
                else{
                    oWriter.println("<h2>Sorry....You have no appointments :</h2>");
                }
                do{
                    date = new Date(rs.getLong(1));
                    date1 = new Date(rs.getLong(2));
                    oWriter.println("<h3>"+i+". "+sdf.format(date)+"\t" + sdf.format(date1) + " \n</h3>");
                    i++;
                }while(rs.next());
                oWriter.println("<form action='./logout'><input type='submit' value='Log Out' /></form>");
                oWriter.println("</body></html>");
                

        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
}
