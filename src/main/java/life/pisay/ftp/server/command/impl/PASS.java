package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.data.Login;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.utils.StringUtils;

import java.io.IOException;

/**
 * 系统登录密码
 *
 * @author Viices Cai
 * @time 2021/11/1
 */
public class PASS implements Command {

    @Override
    public String name() {
        return "PASS";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        FtpSession session = connection.getFtpSession();
        session.reset();

        Login login = session.getLogin();

        if (login == null || login.getUserName() == null){
            connection.send(FtpReply.REPLY_503.getCode(), FtpReply.REPLY_503.getInfo());
            return true;
        }

        if (StringUtils.isNull(args)) {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            return true;
        }

        login.setPassWord(args);

        if (login.getUserName().equals(login.getPassWord())) {
            connection.send(FtpReply.REPLY_230.getCode(), FtpReply.REPLY_230.getInfo());
            login.setLoggedIn(true);

        } else {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
        }

        return true;
    }
}
