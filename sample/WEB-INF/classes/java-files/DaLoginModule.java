import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

public class DaLoginModule implements LoginModule {

    static Map<Object, ArrayList<DaPrincipal>> auth = new HashMap<>();
    static Map<Object, Boolean> data = new HashMap<>();
    private CallbackHandler callbackHandler = null;
    public Subject subject = null;
    public ArrayList<DaPrincipal> daPrincipal = new ArrayList<>();
    DaPrincipal dap = null;
    public userPrincipal user = null;
    boolean success;
    String[] tname = { "admin", "doctors", "patient" };
    ArrayList<DaPrincipal> roles = new ArrayList<>();
    ArrayList<DaPrincipal> otproles;



    String name, pass, otp;
    JNI_call auth_flag = new JNI_call();
    totp TOTP = new totp();
    String jdbcURL = "jdbc:postgresql://localhost:5432/managementDB";
    String username = "postgres";
    String password = "1763";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
            Map<String, ?> options) {

        this.subject = subject;
        this.callbackHandler = callbackHandler;

        try {
            conn = DriverManager.getConnection(jdbcURL, username, password);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login() throws LoginException {
        System.out.println(callbackHandler);

        boolean flag = false;
        Callback[] callbackArray = new Callback[2];
        callbackArray[0] = new NameCallback("username");
        callbackArray[1] = new PasswordCallback("password", false);
        System.out.println(callbackArray.length);

        try {
            callbackHandler.handle(callbackArray);
            name = ((NameCallback) callbackArray[0]).getName();
            pass = new String(((PasswordCallback) callbackArray[1]).getPassword());

            System.out.println("Name and password during the j_security_check : " + name + " " + pass + "end");

            // Checking the data when username is otp and password empty
            if (isNumeric(name)) {
                System.out.println("tname inside otp verification");
                otproles = (ArrayList<DaPrincipal>) auth.get(pass);
                getKey(Authenticate.details.get(pass));
                // check the otp with google authenticator
                if (rs.next()) {
                    if (name.equals(TOTP.main(rs.getString(1)))) {
                        for (int i = 0; i < otproles.size(); i++) {
                            if (otproles.get(i).getName().equals(Authenticate.details.get(pass))) {
                                daPrincipal.add(otproles.get(i));
                                break;
                            }
                        }
                        user = new userPrincipal(pass);
                        flag = true;
                    } else {
                        Authenticate.details.remove(pass);
                    }
                }
            }

            // database check for user and password
            else{
                String x = auth_flag.get_auth(name, pass);
                    if((x!=null)){
                        System.out.println("success in calling native method : " + x);
                        dap = new DaPrincipal(x);
                        roles.add(dap);
                        data.put(name, true);
                        auth.put(name, roles);
                    }
            }
            System.out.println("success in Dalogin module " + flag);
            return flag;

        } catch (Exception e) {
            System.out.println("Error in login");
            e.printStackTrace();
        }

        return flag;
    }





    @Override
    public boolean commit() throws LoginException {
        boolean flag = false;
        System.out.println("inside commit method");
        for (int i = 0; i < daPrincipal.size(); i++) {
            if (subject != null && !subject.getPrincipals().contains(daPrincipal.get(i))) {
                subject.getPrincipals().add(daPrincipal.get(i));
            }
        }
        subject.getPrincipals().add(user);
        System.out.println(subject);
        System.out.println("[+] subject in string" + subject.toString());

        flag = true;
        return flag;
    }

    @Override
    public boolean abort() throws LoginException {
        if (subject != null && !subject.getPrincipals().contains(dap))
            subject.getPrincipals().remove(dap);
        subject = null;
        daPrincipal = null;
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        boolean flag = false;
        System.out.println("b4 loop -> " + subject);

        System.out.println("Inside logout method " + daPrincipal.get(0));
        subject.getPrincipals().remove(daPrincipal.get(0));
        System.out.println("roles cleared");

        subject.getPrincipals().remove(user);
        System.out.println(subject);
        subject = null;
        daPrincipal.clear();
        flag = true;

        return flag;
    }

    void data(String tname) {
        try {
            String query = String.format("select email,password from %s;", tname);
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void getKey(String tname) {
        try {
            String query = String.format("select authkey from %s where email = '%s';", tname, pass);
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isNumeric(String num) {
        try{
            int val = Integer.parseInt(num);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }


    }
}
