package life.pisay.ftp.server.core;

import life.pisay.ftp.server.command.CommandFactory;
import life.pisay.ftp.server.command.FtpCommandFactoryConstructor;
import life.pisay.ftp.server.utils.LoggerUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * FTP 服务
 *
 * @author Viices Cai
 * @time 2021/10/27
 */
@Slf4j
public class FtpServer implements Runnable {

    /**
     * 服务端口
     */
    private int port = 21;

    /**
     * 线程池
     */
    private final Executor threadPool = Executors.newCachedThreadPool();

    /**
     * 指令工厂
     */
    private CommandFactory commands;

    /**
     * 服务器会话
     */
    private ServerSession serverSession;

    public FtpServer() { }

    public FtpServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        log.info(LoggerUtils.printInfo("FTP Server Start"));

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);

            commands = new FtpCommandFactoryConstructor().createCommandFactory();
            serverSession = new ServerSession(new DataTransferGenerator());

            while (true) {
                Socket accept = serverSocket.accept();

                // 连接超时时间
                accept.setSoTimeout(60 * 1000);
                threadPool.execute(new FtpSocket(accept, this));
            }

        } catch (IOException e) {
            log.error(e.getMessage());

        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }

            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 获取指令集合
     *
     * @return 指令工厂
     */
    public CommandFactory getCommands() {

        return commands;
    }

    /**
     * 获取服务器会话
     *
     * @return 服务器会话
     */
    public ServerSession getServerSession() {
        return serverSession;
    }
}
