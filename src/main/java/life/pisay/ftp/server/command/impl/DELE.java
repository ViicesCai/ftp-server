package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 删除指定的文件
 *
 * @author CAI
 * @time 2021/11/12
 */
@Slf4j
public class DELE implements Command {

    @Override
    public String name() {
        return "DELE";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        connection.getFtpSession().reset();

        if (StringUtils.isNull(args)) {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            return true;
        }

        FtpFile file = connection.getFtpSession().getFileSystemView().getFile(args);
        if (file == null) {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
            return true;
        }

        if (file.isDirectory()) {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
            return true;
        }

        if (!file.isRemovable()) {
            connection.send(FtpReply.REPLY_450.getCode(), FtpReply.REPLY_450.getInfo());
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
