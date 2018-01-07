package crawler;

public class Utils {
    public static Double toDouble(String s) {
        if (!Character.isDigit(s.charAt(0)))
            s = s.substring(1) ;
        s =  s.replace(",", "") ;
        return Double.parseDouble(s) ;
    }
}
