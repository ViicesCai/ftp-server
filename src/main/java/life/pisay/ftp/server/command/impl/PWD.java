package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FileSystemView;
import life.pisay.ftp.server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 显示当前工作目录
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
@Slf4j
public class PWD implements Command {

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {
        return "PWD";
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
        FtpSession session = connection.getFtpSession();

        FileSystemView fileSystemView = session.getFileSystemView();

        String currDir = fileSystemView.getWorkingDirectory().getAbsolutePath();

        log.info("the current dir : {}", currDir);

        connection.send(FtpReply.REPLY_257.getCode(), StringUtils.contact("\"", currDir, "\" is current directory."));

        return true;
    }
}

