package life.pisay.ftp.server.core.impl;

import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpServer;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * IO FTP 连接
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public class IOFtpConnection implements FtpConnection {

    private final Socket socket;

    private final FtpSession session;

    private final BufferedReader input;

    private final BufferedWriter output;

    private final Logger LOG = LoggerFactory.getLogger(FtpConnection.class);

    public IOFtpConnection(Socket socket, FtpServer server) throws IOException {
        this.socket = socket;
        this.session = new FtpSession(server.getServerSession(), socket);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public FtpSession getFtpSession() {
        return session;
    }

    /**
     * 发送
     *
     * @param msg 消息
     * @throws IOException 异常
     */
    @Override
    public void send(String msg) throws IOException {
        output.write(formatMsg(msg));
        output.flush();
    }

    /**
     * 发送
     *
     * @param code 指令码
     * @param msg  消息
     * @throws IOException 异常
     */
    @Override
    public void send(int code, String msg) throws IOException {
        output.write(formatMsg(String.valueOf(code), " ", msg));
        output.flush();
    }

    /**
     * 读取
     *
     * @throws IOException 异常
     */
    @Override
    public String read() throws IOException {
        return input.readLine();
    }

    /**
     * 格式化信息
     *
     * @param msgs 消息参数
     * @return 格式化信息
     */
    @Override
    public String formatMsg(String... msgs) {
        StringBuilder builder = new StringBuilder();

        for (String msg : msgs) {
            builder.append(msg);
        }

        LOG.info("SEND MSG INFO : [{}]", builder);

        builder.append("\r\n");

        return builder.toString();
    }

    /**
     * 连接关闭
     */
    @Override
    public void close() {
        IOUtils.close(output);
        IOUtils.close(input);
        IOUtils.close(socket);
    }
}
