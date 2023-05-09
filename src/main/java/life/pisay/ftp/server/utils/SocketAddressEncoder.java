package life.pisay.ftp.server.utils;

import life.pisay.ftp.server.exception.IllegalInetAddressException;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
 * IP地址译码器
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public class SocketAddressEncoder {

    /**
     * 转换IP位数(每位为八位二进制数，即：0~255)
     *
     * @param str IP位数字符串
     * @return IP位数整型
     */
    private static Integer convertIpDigits(String str) {
        int i = Integer.parseInt(str);

        // ip地址位数的最小数不能小于0
        if (i < 0) {
            throw new IllegalArgumentException("IP digits can not be less than 0");

            // ip地址位数的最大数不能大于255
        } else if (i > 255) {
            throw new IllegalArgumentException("IP digits can not be larger than 255");
        }

        return i;
    }

    /**
     * 解码
     *
     * @param str ip地址字符串(包含端口)，eg: A1,A2,A3,A4,P1,P2(p1 high, p2 low)
     * @return IP地址
     */
    public static InetSocketAddress decode(String str) throws UnknownHostException {
        StringTokenizer st = new StringTokenizer(str, ",");

        if (st.countTokens() != 6) {
            throw new IllegalInetAddressException("Illegal amount of ip Digits");
        }

        StringBuilder builder = new StringBuilder();

        // 解析IP地址
        try {
            builder.append(convertIpDigits(st.nextToken()));
            builder.append('.');
            builder.append(convertIpDigits(st.nextToken()));
            builder.append('.');
            builder.append(convertIpDigits(st.nextToken()));
            builder.append('.');
            builder.append(convertIpDigits(st.nextToken()));

        } catch (IllegalArgumentException e) {
            throw new IllegalInetAddressException(e.getMessage());
        }

        InetAddress addr = InetAddress.getByName(builder.toString());

        int port = 0;

        try {
            int high = convertIpDigits(st.nextToken());
            int low = convertIpDigits(st.nextToken());

            // 高位左移八位 = high * 256
            // 按位与
            port = (high << 8) | low;

        } catch (IllegalArgumentException e) {
            throw new IllegalInetAddressException("Invalid data port: " + port);
        }

        return new InetSocketAddress(addr, port);
    }

    /**
     * 编码
     *
     * @param address 包含端口的 IP 地址
     * @return ip地址字符串(包含端口)，eg: A1,A2,A3,A4,P1,P2(p1 high, p2 low)
     */
    public static String encode(InetSocketAddress address) {
        InetAddress servAddr = address.getAddress();
        int servPort = address.getPort();

        return servAddr.getHostAddress().replace('.', ',') + ','
                + (servPort >> 8) + ',' + (servPort & 0xFF);
    }

    private SocketAddressEncoder() {}
}
