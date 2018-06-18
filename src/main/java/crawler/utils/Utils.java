package crawler.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static Double toDouble(String s) {
        if (!Character.isDigit(s.charAt(0)))
            s = s.substring(1) ;
        s =  s.replace(",", "") ;
        return Double.parseDouble(s) ;
    }

    public static Date dateFrom(String s ) throws ParseException {
        return dateFormat.parse(s) ;
    }

    public static String toDateString(Date d ) throws ParseException {
        return dateFormat.format(d) ;
    }
}
