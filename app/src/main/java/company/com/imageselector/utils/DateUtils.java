package company.com.imageselector.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Bivol Pavel on 16.08.2017.
 */

public class DateUtils {
    public static String getCurrentTime(){

        String date;
        String time;

        TimeZone tz = TimeZone.getTimeZone("GMT+03:00");
        Calendar c = Calendar.getInstance(tz);

        SimpleDateFormat df = new SimpleDateFormat("d/M/yy", new Locale("RO RO"));
        String timeFormat = "%02d";

        date = df.format(c.getTime());

        time = String.format(timeFormat , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format(timeFormat , c.get(Calendar.MINUTE));

        return date + " " + time;
    }

}
