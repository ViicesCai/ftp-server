package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.exception.IllegalInetAddressException;
import life.pisay.ftp.server.exception.IllegalPortException;
import life.pisay.ftp.server.utils.SocketAddressEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * 主动连接
 *
 * @author Viices Cai
 * @time 2021/11/1
 */
@Slf4j
public class PORT implements Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {
        return "PORT";
    }

    /**
     * 执行方法
     *
     * @param connection FTP 连接
     * @param args 参数
     * @return 执行结果
     * @throws IOException 异常
     */
    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        InetSocketAddress address;
        try {
            address = SocketAddressEncoder.decode(args);

            if (address.getPort() == 0) {
                throw new IllegalPortException("PORT port must not be 0");
            }

        } catch (IllegalInetAddressException e) {
            log.error("Bad IP address : {}", e.getMessage());

            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            return false;

        } catch (IllegalPortException e) {
            log.error("Bad IP port : {}", e.getMessage());

            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            return false;

        } catch (UnknownHostException e) {
            log.error("Unknown host : {}", e.getMessage());

            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            return false;
        }

        return true;
    }
}
