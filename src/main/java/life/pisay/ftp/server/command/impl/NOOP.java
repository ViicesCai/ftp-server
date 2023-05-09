package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.utils.StringUtils;

import java.io.IOException;

/**
 * 保持连接
 *
 * @author Viices Cai
 * @time 2021/11/17
 */
public class NOOP implements Command {

    @Override
    public String name() {
        return "NOOP";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {
        connection.getFtpSession().reset();

        connection.send(FtpReply.REPLY_200.getCode(), StringUtils.repalce(FtpReply.REPLY_200.getInfo(), name()));

        return true;
    }
}
