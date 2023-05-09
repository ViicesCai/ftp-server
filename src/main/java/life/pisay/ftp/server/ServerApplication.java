package life.pisay.ftp.server;

import life.pisay.ftp.server.core.FtpServer;

/**
 * 服务应用
 *
 * @author Viices Cai
 * @time 2023/4/22
 */
public class ServerApplication {

    public static void main(String[] args) {

        FtpServer server = new FtpServer();
        server.run();
    }
}
