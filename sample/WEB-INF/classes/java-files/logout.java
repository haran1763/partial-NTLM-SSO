import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class logout extends HttpServlet{
    public void service(HttpServletRequest req,HttpServletResponse res){
       try {

          req.getSession().invalidate();
          req.getSession().removeAttribute("JSESSSIONID");

        // Cookie[] cookies = req.getCookies();
        // if(cookies!=null)
        // {
        //   for(Cookie cookie:cookies){
        //     System.out.println(cookie.getName());
        //     if(cookie.getName().equals("JSESSIONID")){
        //       cookie.setValue("");
        //     }
        //     System.out.println("cookie expired" + cookie.getValue());
        // }
        // } 
         res.sendRedirect("/sample/index.html");
       } catch (Exception e) {
        System.out.println("Error in Logging out");
        e.printStackTrace();
       }
    }
}
