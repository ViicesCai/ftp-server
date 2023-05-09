package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FtpFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 删除指定路径下的目录
 *
 * @author Viices Cai
 * @time 2021/11/12
 */
@Slf4j
public class RMD implements Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {
        return "RMD";
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

        if (args == null) {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            return true;
        }

        FtpFile file = connection.getFtpSession().getFileSystemView().getFile(args);
        if (file == null) {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
            return true;
        }

        if (!file.isDirectory()) {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
            return true;
        }

        FtpFile workingDirectory = connection.getFtpSession().getFileSystemView().getWorkingDirectory();
        if (file.equals(workingDirectory)) {
            connection.send(FtpReply.REPLY_450.getCode(), FtpReply.REPLY_450.getInfo());
            return true;
        }

        if (!file.isRemovable()) {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
            return true;
        }

        if (file.delete()) {
            connection.send(FtpReply.REPLY_250.getCode(), FtpReply.REPLY_250.getInfo());

        } else {
            connection.send(FtpReply.REPLY_450.getCode(), FtpReply.REPLY_450.getInfo());
        }

        return true;
    }
}
