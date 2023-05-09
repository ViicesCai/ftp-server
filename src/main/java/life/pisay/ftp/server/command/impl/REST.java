package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 从指定位置恢复文件传输
 *
 * @author Viices Cai
 * @time 2021/12/8
 */
@Slf4j
public class REST implements Command {

    @Override
    public String name() {

        return "REST";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {
        if (!StringUtils.hasLength(args)) {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());

            return true;
        }

        FtpSession session = connection.getFtpSession();

        session.reset();

        // 用于记录分段传输的数据长度
        long chunkSize;

        try {
            chunkSize = Long.parseLong(args);

            if (chunkSize < 0) {

                connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());

            } else {

                session.setChunkSize(chunkSize);
                connection.send(FtpReply.REPLY_350.getCode(), FtpReply.REPLY_350.getInfo());
            }

        } catch (NumberFormatException e) {
            log.warn("Argument parse error {}", e.toString());
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
        }

        return true;
    }
}
