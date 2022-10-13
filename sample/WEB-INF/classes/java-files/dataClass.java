import java.util.Map;


public class dataClass{
    static Map<String, Object> data;

    public void setMap(Object req,String username){
        data.put(username, req);
    }
}
