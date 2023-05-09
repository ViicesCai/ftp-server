package life.pisay.ftp.server.command;

import life.pisay.ftp.server.core.FtpConnection;

import java.io.IOException;

/**
 * 命令接口
 *
 * @author Viices Cai
 * @time 2023/4/22
 */
public interface Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    String name();

    /**
     * 执行方法
     *
     * @param connection FTP 连接
     * @param args 参数
     * @return 执行结果
     * @throws IOException 异常
     */
    Boolean execute(FtpConnection connection, String args) throws IOException;
}
