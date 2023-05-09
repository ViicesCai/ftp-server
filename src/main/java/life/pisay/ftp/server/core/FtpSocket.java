package life.pisay.ftp.server.core;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.command.CommandFactory;
import life.pisay.ftp.server.core.impl.IOFtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.utils.IOUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/**
 * FTP Socket
 *
 * @author Viices Cai
 * @time 2021/10/29
 */
@Slf4j
public class FtpSocket implements Runnable {

    private final FtpServer server;

    private final Socket socket;

    private FtpConnection connection;

    FtpSocket(Socket socket, FtpServer server) {
        super();
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            connection = new IOFtpConnection(socket, server);

            String line;

            connection.send(FtpReply.REPLY_220.getCode(), "Welcome using Jiffy FTP service!");

            while ((line = connection.read()) != null) {
                FtpRequest request = new FtpRequest(line);
                CommandFactory commands = server.getCommands();

                Command command = commands.getCommand(request.getCommand());

                log.info("INFO LINE : [{}]", request.getLine());

                if (command != null) {

                    if (!command.execute(connection, request.getArgs())) {
                        break;
                    }

                } else {
                    log.error("CMD NOT IMPL");
                    connection.send(FtpReply.REPLY_502.getCode(), FtpReply.REPLY_502.getInfo());
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage());

        } finally {
            IOUtils.close(connection);
        }
    }
}
