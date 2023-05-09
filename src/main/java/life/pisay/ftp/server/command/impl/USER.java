package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.data.Login;
import life.pisay.ftp.server.enums.FtpReply;

import java.io.IOException;

/**
 * 系统登录的用户名
 *
 * @author Viices Cai
 * @time 2021/10/27
 */
public class USER implements Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {
        return "USER";
    }

    /**
     * 执行方法
     *
     * @param connection 会话接口
     * @param args    参数
     * @return 执行结果
     * @throws IOException 异常
     */
    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        FtpSession session = connection.getFtpSession();

        if (args == null) {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            return true;
        }

        Login login = session.getLogin();

        if (login != null) {
            if (!login.isLoggedIn()) {
                if (args.equals(login.getUserName())) {
                    connection.send(FtpReply.REPLY_230.getCode(), FtpReply.REPLY_230.getInfo());
                    return true;

                } else {
                    connection.send(FtpReply.REPLY_530.getCode(), FtpReply.REPLY_530.getInfo());
                    return true;
                }
            }
        }

        Login user = new Login();
        user.setUserName(args);
        session.setLogin(user);

        connection.send(FtpReply.REPLY_331.getCode(), FtpReply.REPLY_331.getInfo());
        return true;
    }
}
