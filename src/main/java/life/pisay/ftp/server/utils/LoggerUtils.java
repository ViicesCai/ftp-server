package life.pisay.ftp.server.utils;

/**
 * 日志工具类
 *
 * @author Viices Cai
 * @time 2021/10/27
 */
public class LoggerUtils {

    public static String printInfo(String info) {
        StringBuilder builder = new StringBuilder();
        builder.append("<========== ");
        builder.append(info);
        builder.append(" ==========>");

        return builder.toString();
    }

    private LoggerUtils() {}
}
