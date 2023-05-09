package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpReply;

import java.io.IOException;

/**
 * 中止
 * 中止先前的FTP服务命令和任何相关的数据传输
 *
 * @author Viices Cai
 * @time 2021/12/27
 */
public class ABOR implements Command {

    @Override
    public String name() {
        return "ABOR";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        FtpSession session = connection.getFtpSession();
        session.reset();
        session.getConnectionFactory().close();

        connection.send(FtpReply.REPLY_226.getCode(), FtpReply.REPLY_226.getInfo());

        return true;
    }
}

