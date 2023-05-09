package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FileSystemView;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 返回上级目录
 *
 * @author Viices Cai
 * @time 2021/11/11
 */
@Slf4j
public class CDUP implements Command {

    @Override
    public String name() {
        return "CDUP";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        connection.getFtpSession().reset();

        FileSystemView systemView = connection.getFtpSession().getFileSystemView();

        boolean isFailure = systemView.changeWorkingDirectory("..");
        if (isFailure) {    
            connection.send(FtpReply.REPLY_250.getCode(), FtpReply.REPLY_250.getInfo());

        } else {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
        }

        log.info("The current dir is {}", systemView.getWorkingDirectory().getAbsolutePath());

        return true;
    }
}
