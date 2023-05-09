package life.pisay.ftp.server.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 日期工具类
 *
 * @author Viices Cai
 * @time 2021/11/8
 */
public class DateUtils {

    /**
     * UTC 时区
     */
    private static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");

    /**
     * 月份
     */
    private final static String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May",
            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    /**
     * 间隔符
     */
    private final static char BLANK = ' ';

    /**
     * 六个月：183L * 24L * 60L * 60L * 1000L;
     */
    private final static Long SIX_MONTH = 15811200000L;

    /**
     * 获取 UNIX 格式的日期
     *
     * @param millis 毫秒数
     * @return UNIX 格式的日期
     */
    public static String getUnixDate(long millis) {
        if (millis < 0) {
            return "------------";
        }

        StringBuilder builder = new StringBuilder(16);
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(millis);

        // 月份
        builder.append(MONTHS[cal.get(Calendar.MONTH)]);
        builder.append(BLANK);

        // 天数
        int day = cal.get(Calendar.DATE);
        if (day < 10) {
            builder.append(BLANK);
        }
        builder.append(day);
        builder.append(BLANK);

        long nowTime = System.currentTimeMillis();
        if (Math.abs(nowTime - millis) > SIX_MONTH) {

            // 年
            int year = cal.get(Calendar.YEAR);
            builder.append(' ');
            builder.append(year);
        } else {

            // 小时
            int hh = cal.get(Calendar.HOUR_OF_DAY);
            if (hh < 10) {
                builder.append('0');
            }
            builder.append(hh);
            builder.append(':');

            // 分钟
            int mm = cal.get(Calendar.MINUTE);
            if (mm < 10) {
                builder.append('0');
            }

            builder.append(mm);
        }
        return builder.toString();
    }

    /**
     * 获取 MS-DOS 格式的日期
     *
     * @param millis 毫秒数
     * @return MS-DOS 格式日期
     */
    public static String getWindowDate(long millis) {
        if (millis < 0) {
            return "00-00-00  00:00AM";
        }

        StringBuilder builder = new StringBuilder(17);

        Date date = new Date(millis);
        Instant instant = date.toInstant();

        // 获取当前日期
        LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDate localDate = dateTime.toLocalDate();
        LocalTime localTime = dateTime.toLocalTime();

        // 月份
        builder.append(localDate.format(DateTimeFormatter.ofPattern("MM-dd-")));

        // 年
        String year = String.valueOf(localDate.getYear());
        builder.append(year.substring(2));
        builder.append(BLANK);
        builder.append(BLANK);

        // 时间
        int time = localTime.getHour();
        String timeStamp;
        if (time <= 11) {
            timeStamp = "AM";

        } else {
            timeStamp = "PM";
        }
        builder.append(localTime.format(DateTimeFormatter.ofPattern("hh:mm")));
        builder.append(timeStamp);

        return builder.toString();
    }

    private DateUtils() { }
}
