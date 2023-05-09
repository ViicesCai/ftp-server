package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 终止 FTP 服务，执行时若不存在传输的文件则关闭连接
 *
 * @author Viices Cai
 * @time 2021/11/11
 */
@Slf4j
public class QUIT implements Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {
        return "QUIT";
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

        connection.send(FtpReply.REPLY_221.getCode(), "Thank you for using it, Good bye.");
        connection.getFtpSession().getConnectionFactory().close();

        return false;
    }
}
