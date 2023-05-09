package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FtpFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 指定需要重新命名的文件的原始路径名
 * 后面必须马上接着 "RNTO" 命令，来指定新的文件路径
 *
 * @author Viices Cai
 * @time 2021/11/11
 */
@Slf4j
public class RNFR implements Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {
        return "RNFR";
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

        if (args == null) {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_550.getInfo());
            return true;
        }

        FtpFile renameFile = connection.getFtpSession().getFileSystemView().getFile(args);

        if (renameFile == null) {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());

        } else {
            connection.getFtpSession().setRenameFrom(renameFile);
            connection.send(FtpReply.REPLY_350.getCode(), FtpReply.REPLY_350.getInfo());
        }

        return true;
    }
}
