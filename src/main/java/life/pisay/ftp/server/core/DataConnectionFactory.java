package life.pisay.ftp.server.core;

import life.pisay.ftp.server.exception.DataConnectionException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * 数据连接工厂
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public interface DataConnectionFactory {

    /**
     * 初始化主动模式连接
     *
     * @param address 客户端地址与端口
     */
    void initActiveDataConnection(InetSocketAddress address);

    /**
     * 初始化被动模式连接
     *
     * @return 服务端数据端口
     */
    InetSocketAddress initPassiveDataConnection() throws UnknownHostException, DataConnectionException;

    /**
     * 打开数据连接
     *
     * @return 开放的数据连接
     */
    DataConnection open() throws IOException;

    /**
     * 是否使用安全加密
     *
     * @return true:使用加密连接
     */
    Boolean isSecure();

    /**
     * 关闭数据连接
     */
    void close();
}
