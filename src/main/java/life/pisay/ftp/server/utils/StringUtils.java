package life.pisay.ftp.server.utils;


import java.util.Map;

/**
 * 字符串工具类
 *
 * @author Viices Cai
 * @time 2021/11/1
 */
public class StringUtils {

    /**
     * 换行符
     */
    public static final String NEWLINE = "\r\n";

    /**
     * 替换字符串
     *
     * @param source 字符串
     * @param arg 参数
     * @return 替换后的字符串
     */
    public static String repalce(String source, String arg) {
        int startIndex = 0;

        int openIndex = source.indexOf('{', startIndex);
        if (openIndex == -1) {
            return source;
        }

        int closeIndex = source.indexOf("}", startIndex);
        if (closeIndex == -1) {
            return source;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(source, startIndex, openIndex);

        while (true) {
            builder.append(arg);

            startIndex = closeIndex + 1;
            openIndex = source.indexOf('{', startIndex);
            if (openIndex == -1) {
                builder.append(source.substring(startIndex));
                break;
            }

            closeIndex = source.indexOf('}', startIndex);
            if ((closeIndex == -1) || (openIndex > closeIndex)) {
                builder.append(source.substring(startIndex));
                break;
            }
            builder.append(source, startIndex, openIndex);
        }

        return builder.toString();
    }

    /**
     * 替换字符串
     *
     * @param source 字符串
     * @param args 参数 MAP
     * @return 替换后的字符串
     */
    public static String repalce(String source, Map<String, String> args) {
        int startIndex = 0;

        int openIndex = source.indexOf('{', startIndex);
        if (openIndex == -1) {
            return source;
        }

        int closeIndex = source.indexOf("}", startIndex);
        if (closeIndex == -1) {
            return source;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(source, startIndex, openIndex);

        while (true) {
            String key = source.substring(openIndex + 1, closeIndex);
            Object val = args.get(key);
            if (val != null) {
                builder.append(val);
            }

            startIndex = closeIndex + 1;
            openIndex = source.indexOf('{', startIndex);
            if (openIndex == -1) {
                builder.append(source.substring(startIndex));
                break;
            }

            closeIndex = source.indexOf('}', startIndex);
            if ((closeIndex == -1) || (openIndex > closeIndex)) {
                builder.append(source.substring(startIndex));
                break;
            }
            builder.append(source, startIndex, openIndex);
        }

        return builder.toString();
    }

    /**
     * 字符串拼接
     *
     * @param strs 可变字符串
     * @return 拼接后的字符串
     */
    public static String contact(String ...strs) {
        StringBuilder builder = new StringBuilder();

        for (String msg : strs) {
            builder.append(msg);
        }

        return builder.toString();
    }

    /**
     * 字符串有长度
     *
     * @param str 字符串
     * @return true:字符串有长度
     */
    public static boolean hasLength(String str) {

        return str != null && !str.isEmpty();
    }

    /**
     * 字符串有内容
     *
     * @param str 字符串
     * @return true:字符串有内容
     */
    public static boolean hasText(String str) {
        if (hasLength(str)) {
            str = str.trim();

            return hasLength(str);
        }

        return false;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return true:字符串为空
     */
    public static boolean isNull(String str) {

        if (str == null) {
            return true;
        }

        if (!"null".equalsIgnoreCase(str)) {
            str = str.trim();

        } else {
            str= "";
        }

        return "".equals(str);
    }

    /**
     * 格式化行字符串
     *
     * @param str 字符串
     * @return 带换行符的字符串
     */
    public static String formatLine(String str) {

        return contact(str, NEWLINE);
    }
}
