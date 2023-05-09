package life.pisay.ftp.server.utils;

/**
 * 系统工具类
 *
 * @author Viices Cai
 * @time 2021/11/18
 */
public class OSUtils {

    private static final String OS_INFO = System.getProperty("os.name").toLowerCase();

    /**
     * 获取系统名称
     *
     * @return 系统名称
     */
    public static String getSystemName() {

        String systemName;

        if (!StringUtils.hasText(OS_INFO)) {
            systemName = "unknown";

        } else {
            systemName = OS_INFO.replace(' ', '-');
        }

        return systemName.toUpperCase();
    }

    private OSUtils() { }
}
