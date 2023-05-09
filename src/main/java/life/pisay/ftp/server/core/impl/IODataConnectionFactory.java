package life.pisay.ftp.server.core.impl;

import life.pisay.ftp.server.core.DataConnection;
import life.pisay.ftp.server.core.DataConnectionFactory;
import life.pisay.ftp.server.core.DataTransferGenerator;
import life.pisay.ftp.server.exception.DataConnectionException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * IO 数据传输工厂
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
@Slf4j
public class IODataConnectionFactory implements DataConnectionFactory {

    /**
     * 数据连接 ServerSocket
     */
    private ServerSocket serverSocket;

    /**
     * 数据连接 Socket
     */
    private Socket dataSocket;

    /**
     * 数据连接 IP 地址
     */
    private final InetAddress address;

    /**
     * 数据连接端口
     */
    private Integer port;

    /**
     * FTP 控制服务 端口
     */
    private final DataTransferGenerator generator;

    /**
     * 被动模式
     */
    private Boolean passive = false;

    /**
     * 安全连接
     */
    private final Boolean secure = false;

    public IODataConnectionFactory(DataTransferGenerator generator, Socket socket) {

        this.address = socket.getLocalAddress();
        this.generator = generator;
    }

    /**
     * 初始化主动模式连接
     *
     * @param address 客户端地址与端口
     */
    @Override
    public void initActiveDataConnection(InetSocketAddress address) { }

    /**
     * 初始化被动模式连接
     *
     * @return 服务端数据端口
     */
    @Override
    public synchronized InetSocketAddress initPassiveDataConnection() throws DataConnectionException {
        try {
            this.port = generator.obtain();
            serverSocket = new ServerSocket(port, 0, address);

        } catch (IOException e) {
            serverSocket = null;
            close();

            throw new DataConnectionException("Failed to initate passive data connection:" + e, e);
        }

        passive = true;

        return new InetSocketAddress(address.getHostAddress(), port);
    }

    /**
     * 打开数据连接
     *
     * @return 开放的数据连接
     */
    @Override
    public DataConnection open() throws IOException {

        return new IODataConnection(onCreate(), this);
    }

    /**
     * 是否使用安全加密
     *
     * @return true:使用加密连接
     */
    @Override
    public Boolean isSecure() {

        return secure;
    }

    /**
     * 关闭数据连接
     */
    @Override
    public synchronized void close() {
        if (dataSocket != null) {
            try {
                dataSocket.close();

            } catch (IOException e) {
                log.warn("FTP DataConnection.close : {}", e.getMessage());
            }

            dataSocket = null;
        }

        if (serverSocket != null) {
            try {
                serverSocket.close();

            } catch (IOException e) {
                log.warn("FTP DataConnection.close : {}", e.getMessage());
            }

            serverSocket = null;
        }

        if (port != null) {
            if (!generator.release(port)) {
                log.warn("Data Connection Not Release");
            }
        }
    }

    /**
     * 创建数据连接
     *
     * @return 用于数据传输的 Socket
     */
    private Socket onCreate() throws IOException {
        dataSocket = null;

        try {
            // 被动模式
            if (passive) {
                // 不启用安全连接
                if (!secure) {
                    log.debug("Opening passive data connection");
                    dataSocket = serverSocket.accept();
                }
            }
        } catch (IOException e) {
            close();
            log.warn("FTP DataConnection.create() ", e);
            throw e;
        }

        return dataSocket;
    }

    public InetAddress getAddress() {
        return address;
    }
}
