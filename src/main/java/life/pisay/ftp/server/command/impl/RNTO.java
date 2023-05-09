package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FtpFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 为 RNFR 指定的文件重命名，RNFR + RNTO 组成完成重命名的动作
 *
 * @author Viices Cai
 * @time 2021/11/11
 */
@Slf4j
public class RNTO implements Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {
        return "RNTO";
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

        try {
            if (args == null) {
                connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
                return true;
            }

            FtpFile renameFile = connection.getFtpSession().getRenameFrom();
            if (renameFile == null) {
                connection.send(FtpReply.REPLY_553.getCode(), FtpReply.REPLY_553.getInfo());
                return true;
            }

            FtpFile toFile = connection.getFtpSession().getFileSystemView().getFile(args);
            if (toFile == null) {
                connection.send(FtpReply.REPLY_553.getCode(), FtpReply.REPLY_553.getInfo());
                return true;
            }

            if (!toFile.isWritable()) {
                connection.send(FtpReply.REPLY_553.getCode(), FtpReply.REPLY_553.getInfo());
                return true;
            }

            if (!renameFile.doesExist()) {
                connection.send(FtpReply.REPLY_553.getCode(), FtpReply.REPLY_553.getInfo());
                return true;
            }

            if (renameFile.move(toFile)) {
                connection.send(FtpReply.REPLY_250.getCode(), FtpReply.REPLY_250.getInfo());

            } else {
                connection.send(FtpReply.REPLY_553.getCode(), FtpReply.REPLY_553.getInfo());
            }

        } finally {
            connection.getFtpSession().setRenameFrom(null);
        }

        return true;
    }
}
