package life.pisay.ftp.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 数据连接
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public interface DataConnection {

    /**
     * 从客户端接收数据
     *
     * @param session FTP 会话
     * @param out 输出流
     * @return 传输数据的长度
     * @throws IOException IO 异常
     */
    Long transferFromClient(FtpSession session, OutputStream out) throws IOException;

    /**
     * 将数据传输到客户端
     *
     * @param session FTP 会话
     * @param in 输入流
     * @return 传输数据的长度
     * @throws IOException IO异常
     */
    Long transferToClient(FtpSession session, InputStream in) throws IOException;

    /**
     * 传输字符串到客户端
     *
     * @param session FTP 会话
     * @param str 字符串
     * @throws IOException IO异常
     */
    void transferToClient(FtpSession session, String str) throws IOException;
}
