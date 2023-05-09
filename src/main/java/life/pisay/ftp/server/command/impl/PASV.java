package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.DataConnectionFactory;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.exception.DataConnectionException;
import life.pisay.ftp.server.utils.SocketAddressEncoder;
import life.pisay.ftp.server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 被动模式
 *
 * @author Viices Cai
 * @time 2021/11/8
 */
@Slf4j
public class PASV implements Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {

        return "PASV";
    }

    /**
     * 执行方法
     *
     * @param connection FTP 连接
     * @param args       参数
     * @return 执行结果
     * @throws IOException 异常
     */
    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {
        FtpSession ftpSession = connection.getFtpSession();

        DataConnectionFactory connectionFactory = ftpSession.getConnectionFactory();

        InetSocketAddress address;
        try {
            address = connectionFactory.initPassiveDataConnection();

        } catch (DataConnectionException e) {
            log.warn(e.getMessage());

            connectionFactory.close();
            connection.send(FtpReply.REPLY_451.getCode(), FtpReply.REPLY_451.getInfo());
            return true;
        }

        connection.send(FtpReply.REPLY_227.getCode(), StringUtils.repalce(FtpReply.REPLY_227.getInfo(), SocketAddressEncoder.encode(address)));

        return true;
    }
}
