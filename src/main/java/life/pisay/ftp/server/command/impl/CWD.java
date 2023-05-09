package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FileSystemView;
import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 该命令改变工作目录到用户指定的目录
 *
 * @author Viices Cai
 * @time 2021/11/8
 */
@Slf4j
public class CWD implements Command {

    @Override
    public String name() {
        return "CWD";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        FtpSession session = connection.getFtpSession();
        session.reset();

        String dirName = "/";
        if (!StringUtils.isNull(args)) {
            dirName = args;
        }

        FileSystemView fileSystemView = session.getFileSystemView();
        boolean isFailure = fileSystemView.changeWorkingDirectory(dirName);

        FtpFile cwd = fileSystemView.getWorkingDirectory();
        if (isFailure) {
            dirName = cwd.getAbsolutePath();
            log.info("current working dir : {}", dirName);

            connection.send(FtpReply.REPLY_250.getCode(), FtpReply.REPLY_250.getInfo());

        } else {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
        }

        return true;
    }
}
