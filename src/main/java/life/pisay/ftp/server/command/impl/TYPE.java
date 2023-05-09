package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpDataType;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 数据类型（A=ASCII，E=EBCDIC，I=binary）
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public class TYPE implements Command {

    private final Logger LOG = LoggerFactory.getLogger(TYPE.class);

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {
        return "TYPE";
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

        if (!StringUtils.hasLength(args)) {
            connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());

            return true;
        }

        FtpSession session = connection.getFtpSession();

        try {
            FtpDataType dataType = FtpDataType.getValue(args.charAt(0));
            session.setDataType(dataType);

        } catch (IllegalArgumentException e) {
            connection.send(FtpReply.REPLY_504.getCode(), FtpReply.REPLY_504.getInfo());

            LOG.error(e.getMessage());
            return true;
        }

        connection.send(FtpReply.REPLY_200.getCode(), StringUtils.repalce(FtpReply.REPLY_200.getInfo(), "TYPE"));
        return true;
    }
}
