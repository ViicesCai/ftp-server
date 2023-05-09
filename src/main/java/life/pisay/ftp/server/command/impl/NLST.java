package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.DataConnection;
import life.pisay.ftp.server.core.DataConnectionFactory;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.core.impl.IODataConnectionFactory;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.io.file.listing.DirectoryLister;
import life.pisay.ftp.server.io.file.listing.LISTFileFormatter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 向客户端返回服务器中工作目录下的目录列表
 *
 * @author Viices Cai
 * @time 2021/11/19
 */
@Slf4j
public class NLST implements Command {

    @Override
    public String name() {
        return "NLST";
    }

    /**
     * 执行方法
     *
     * @param connection FTP 连接
     * @param args       参数
     * @return 执行结果
     * @throws IOException 异常
     */
    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        FtpSession session = connection.getFtpSession();
        session.reset();

        try {
            if (args == null) {
                args = session.getFileSystemView().getWorkingDirectory().getAbsolutePath();
            }

            FtpFile file = session.getFileSystemView().getFile(args);
            if (!file.doesExist()) {
                log.warn("Listing file not exist");
                connection.send(FtpReply.REPLY_450.getCode(), FtpReply.REPLY_450.getInfo());

                return true;
            }

            DataConnectionFactory connectionFactory = session.getConnectionFactory();
            if (connectionFactory instanceof IODataConnectionFactory) {
                InetAddress address = ((IODataConnectionFactory) connectionFactory).getAddress();

                if (address == null) {
                    connection.send(FtpReply.REPLY_503.getCode(), FtpReply.REPLY_503.getInfo());
                    return true;
                }
            }

            connection.send(FtpReply.REPLY_150.getCode(), FtpReply.REPLY_150.getInfo());

            DataConnection dataConnection;
            try {
                dataConnection = connectionFactory.open();

            } catch (IOException e) {
                log.warn("Exception getting the output data stream", e);
                connection.send(FtpReply.REPLY_425.getCode(), FtpReply.REPLY_425.getInfo());
                return true;
            }

            boolean isFailure = false;
            String dirList = new DirectoryLister().listFiles(args, session.getFileSystemView(), new LISTFileFormatter());
            try {
                dataConnection.transferToClient(session, dirList);

            } catch (SocketException e) {
                log.warn("Socket exception during list transfer", e);
                isFailure = true;

                connection.send(FtpReply.REPLY_426.getCode(), FtpReply.REPLY_426.getInfo());

            } catch (IOException e) {
                log.warn("IOException during list transfer", e);
                isFailure = true;

                connection.send(FtpReply.REPLY_551.getCode(), FtpReply.REPLY_551.getInfo());

            } catch (IllegalArgumentException e) {
                log.warn("Illegal list syntax : {}", args, e);
                connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());
            }

            if (!isFailure) {
                connection.send(FtpReply.REPLY_226.getCode(), FtpReply.REPLY_226.getInfo());
            }

        } finally {
            session.getConnectionFactory().close();
        }

        return true;
    }
}
