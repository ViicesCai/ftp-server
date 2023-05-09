package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.DataConnection;
import life.pisay.ftp.server.core.DataConnectionFactory;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.core.impl.IODataConnectionFactory;
import life.pisay.ftp.server.enums.FtpDataType;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.utils.IOUtils;
import life.pisay.ftp.server.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 从服务器中获得文件
 *
 * @author Viices Cai
 * @time 2021/11/9
 */
@Slf4j
public class RETR implements Command {

    private final Logger LOG = LoggerFactory.getLogger(RETR.class);

    /**
     * 指令名称
     *
     * @return 指令名称
     */
    @Override
    public String name() {

        return "RETR";
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

        try {
            String fileName = args;

            if (!StringUtils.hasLength(fileName)) {
                connection.send(FtpReply.REPLY_501.getCode(), FtpReply.REPLY_501.getInfo());

                return true;
            }

            FtpFile file = session.getFileSystemView().getFile(fileName);

            if (file == null) {
                connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
                return true;
            }

            fileName = file.getAbsolutePath();

            if (!file.doesExist()) {
                connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
                return true;
            }

            if (!file.isFile()) {
                connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
                return true;
            }

            if (!file.isReadable()) {
                connection.send(FtpReply.REPLY_550.getCode(), FtpReply.REPLY_550.getInfo());
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

            boolean isFailure = false;
            InputStream in = null;

            DataConnection dataConnection;
            try {
                dataConnection = connectionFactory.open();

            } catch (IOException e) {
                LOG.warn("Exception getting the output data stream", e);
                connection.send(FtpReply.REPLY_425.getCode(), FtpReply.REPLY_425.getInfo());
                return true;
            }

            // 传输大小
            long transSize = 0L;
            try {
                long chunkSize = session.getChunkSize();
                in = openInputStream(session, file, chunkSize);

                transSize = dataConnection.transferToClient(session, in);

                LOG.info("Transfer Size : {}", transSize);

                if (in != null) {
                    in.close();
                }

                LOG.info("File downloaded {}", fileName);
            } catch (SocketException e) {
                LOG.warn("Socket exception during data transfer", e);
                isFailure = true;
                connection.send(FtpReply.REPLY_426.getCode(), FtpReply.REPLY_426.getInfo());

            } catch (IOException e) {
                LOG.warn("IOException during data transfer", e);
                isFailure = true;
                connection.send(FtpReply.REPLY_551.getCode(), FtpReply.REPLY_551.getInfo());

            } finally {
                IOUtils.close(in);
            }

            if (!isFailure) {
                connection.send(FtpReply.REPLY_226.getCode(), FtpReply.REPLY_226.getInfo());
            }

        } finally {
            session.reset();
            session.getConnectionFactory().close();
        }

        return true;
    }

    private InputStream openInputStream(FtpSession session, FtpFile file, Long chunkSize) throws IOException {
        InputStream in = null;

        if (session.getDataType() == FtpDataType.BINARY) {
            in = file.createInputStream(chunkSize);
        }

        return in;
    }
}
