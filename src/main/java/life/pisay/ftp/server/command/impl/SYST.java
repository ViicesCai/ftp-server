package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.utils.OSUtils;
import life.pisay.ftp.server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 返回服务器的系统类型
 *
 * @author Viices Cai
 * @time 2021/11/18
 */
@Slf4j
public class SYST implements Command {

    @Override
    public String name() {
        return "SYST";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        String systemName = OSUtils.getSystemName();

        connection.send(FtpReply.REPLY_215.getCode(), StringUtils.repalce(FtpReply.REPLY_215.getInfo(), systemName));

        return true;
    }
}
