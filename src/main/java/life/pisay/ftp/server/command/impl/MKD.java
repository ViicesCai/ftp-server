package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 创建目录
 *
 * @author Viices Cai
 * @time 2021/11/11
 */
@Slf4j
public class MKD implements Command {

    @Override
    public String name() {
        return "MKD";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        if (StringUtils.isNull(args)) {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            return true;
        }

        FtpSession session = connection.getFtpSession();
        session.reset();

        FtpFile file =  session.getFileSystemView().getFile(args);
        if (file == null) {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
            return true;
        }

        if (file.doesExist()) {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
            return true;
        }

        String fileName = file.getAbsolutePath();
        if (file.mkdir()) {
            connection.send(FtpReply.REPLY_257.getCode(), StringUtils.repalce(FtpReply.REPLY_257.getInfo(), fileName));
            log.info("Directory created : {}", fileName);

        } else {
            connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
        }

        return true;
    }
}
