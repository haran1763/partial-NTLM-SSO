public class JNI_call {
    static {
        try {
            System.load("D:/New folder/New folder/Testdll.dll");
        } catch (Exception e) {
            System.out.println("in native loading");
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public native String get_auth(String username ,String password);
    public static void main(String[] args) {
    System.setProperty("java.library.path", "D:/New folder/New folder");
        String val = new JNI_call().get_auth("hari","Haran@1763");
        System.out.println("val : " + val);
        
    }

}   
