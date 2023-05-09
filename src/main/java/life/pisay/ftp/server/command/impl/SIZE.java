package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.utils.StringUtils;

import java.io.IOException;

/**
 * 文件大小
 *
 * @author Viices Cai
 * @time 2022/1/22
 */
public class SIZE implements Command {

    @Override
    public String name() {
        return "SIZE";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {
        FtpSession session = connection.getFtpSession();

        try {
            String fileName = args;
            if (fileName == null || !StringUtils.hasText(fileName)) {
                connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());

                return true;
            }

            FtpFile file = session.getFileSystemView().getFile(fileName);

            if (file == null) {
                connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());

                return true;
            }

            if (!file.doesExist()) {
                connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());

            } else if (file.isDirectory()) {
                connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());

            } else {
                connection.send(FtpReply.REPLY_213.getCode(), "FILE SIZE " + file.getSize());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
