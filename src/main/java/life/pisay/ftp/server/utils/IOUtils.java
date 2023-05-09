package life.pisay.ftp.server.utils;

import life.pisay.ftp.server.core.FtpConnection;

import java.io.*;
import java.net.Socket;

/**
 * IO 工具类
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public class IOUtils {

    /**
     * 关闭
     *
     * @param in 输出流
     */
    public static void close(InputStream in) {
        onClose(in);
    }

    /**
     * 关闭
     *
     * @param out 输入流
     */
    public static void close(OutputStream out) {
        onClose(out);
    }

    /**
     * 关闭
     *
     * @param reader 字符输出流
     */
    public static void close(Reader reader) {
        onClose(reader);
    }

    /**
     * 关闭
     *
     * @param writer 字符输出流
     */
    public static void close(Writer writer) {
        onClose(writer);
    }

    /**
     * 关闭
     *
     * @param socket socket 连接
     */
    public static void close(Socket socket) {

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭
     *
     * @param conn FTP 连接
     */
    public static void close(FtpConnection conn) {

        if (conn != null) {
            conn.close();
        }
    }

    /**
     * 关闭方法
     *
     * @param closeable 可关闭类
     */
    private static void onClose(Closeable closeable) {

        try {
            if (closeable != null) {
                closeable.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IOUtils() { }
}
