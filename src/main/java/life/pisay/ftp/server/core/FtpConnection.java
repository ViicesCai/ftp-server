package life.pisay.ftp.server.core;

import java.io.IOException;

/**
 * FTP连接
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public interface FtpConnection {

    /**
     * 获取 FTP Session
     *
     * @return 用户的 Ftp Session
     */
    FtpSession getFtpSession();

    /**
     * 发送
     *
     * @param msg 消息
     * @throws IOException 异常
     */
    void send(String msg) throws IOException;

    /**
     * 发送
     *
     * @param code 指令码
     * @param msg 消息
     * @throws IOException 异常
     */
    void send(int code, String msg) throws IOException;

    /**
     * 读取
     *
     * @return 读取信息
     * @throws IOException 异常
     */
    String read() throws IOException;

    /**
     * 格式化信息
     *
     * @param msgs 消息参数
     * @return 格式化信息
     */
    String formatMsg(String... msgs);

    /**
     * 连接关闭
     */
    void close();
}
