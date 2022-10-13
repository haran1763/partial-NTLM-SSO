import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.servlet.*;
import javax.servlet.http.*;

public class Authenticate extends HttpServlet{
    static Map<String,String> details = new HashMap<>();
    String role = null;
    public void service(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{

        try {
            
            if(DaLoginModule.data.get(req.getParameter("j_username"))){


            System.out.println("[+] inside authenticate servlet " + req.getParameter("tname") );

            if(req.getParameter("tname").equals("/sample/Admin/admin.html")){
                if(DaLoginModule.auth.get(req.getParameter("j_username")).get(0).getName().equals("admin")){
                    details.put(req.getParameter("j_username"), "admin");
                    DaLoginModule.data.remove(req.getParameter("j_username"));
                }
                else{
                    DaLoginModule.auth.clear();
                    res.sendRedirect("/sample/loginerr.jsp");
                }
            }
            else if(req.getParameter("tname").equals("/sample/Doctors")){
                if(DaLoginModule.auth.get(req.getParameter("j_username")).get(0).getName()=="doctor"){
                    details.put(req.getParameter("j_username"), "doctor");
                    DaLoginModule.data.remove(req.getParameter("j_username"));
                }
                else{
                    DaLoginModule.auth.clear();
                    res.sendRedirect("/sample/loginerr.jsp");
                }
            }
            else if(req.getParameter("tname").equals("/sample/appointment.jsp")){
                if(DaLoginModule.auth.get(req.getParameter("j_username")).get(0).getName()=="patient"){
                    details.put(req.getParameter("j_username"), "patient");
                    DaLoginModule.data.remove(req.getParameter("j_username"));
                }
                else{
                    DaLoginModule.auth.clear();
                    res.sendRedirect("/sample/loginerr.jsp");
                }
            }



            // switch(DaLoginModule.auth.get(req.getParameter("j_username")).get(0).getName()){
            //     case "admin":details.put(req.getParameter("j_username"), "admin");
            //                  DaLoginModule.data.remove(req.getParameter("j_username"));
            //                  break;
            //     case "doctor":details.put(req.getParameter("j_username"), "doctor");
            //                  DaLoginModule.data.remove(req.getParameter("j_username"));
            //                  break;
            //     case "patient":details.put(req.getParameter("j_username"), "patient");
            //                  DaLoginModule.data.remove(req.getParameter("j_username"));
            //                  break;
            //     default:throw new LoginException();
            // }
            
            System.out.println("[+] inside authenticate servlet tname " + req.getParameter("j_username"));

            
        }    

        else{
            // tname = req.getParameter("tname");
            DaLoginModule.auth.clear();
            res.sendRedirect("/sample/loginerr.jsp");
            
        }
        req.getRequestDispatcher("/Admin/OTP.jsp").include(req, res);
        
    } catch (Exception e) {
        DaLoginModule.auth.clear();
        res.sendRedirect("/sample/loginerr.jsp");
    }
}
}