package life.pisay.ftp.server.core;

import life.pisay.ftp.server.core.impl.IODataConnectionFactory;
import life.pisay.ftp.server.data.Login;
import life.pisay.ftp.server.enums.FtpDataType;
import life.pisay.ftp.server.io.file.FileSystemView;
import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.io.file.impl.NativeFileSystemView;
import life.pisay.ftp.server.user.User;
import life.pisay.ftp.server.utils.FileSystemUtils;

import java.io.File;
import java.net.Socket;

/**
 * FTP 会话：用于记录连接产生的状态
 *
 * @author Viices Cai
 * @time 2021/10/28
 */
public class FtpSession {

    /**
     * 数据类型
     */
    private FtpDataType dataType;

    /**
     * 登录者
     */
    private Login login;

    /**
     * 重命名文件
     */
    private FtpFile renameFrom;

    /**
     * 数据分块大小
     */
    private Long chunkSize = 0L;

    /**
     * 数据连接工厂
     */
    private final DataConnectionFactory connectionFactory;

    /**
     * 文件系统视图
     */
    private final FileSystemView fileSystemView;

    /**
     * 服务会话
     */
    private final ServerSession serverSession;

    public FtpSession(ServerSession serverSession, Socket socket) {
        super();
        this.serverSession = serverSession;
        this.connectionFactory = new IODataConnectionFactory(serverSession.getDataTransferGenerator(), socket);
        this.fileSystemView = new NativeFileSystemView(new User() {

            @Override
            public String getName() {
                return "root";
            }

            @Override
            public String getPassword() {
                return "root";
            }

            @Override
            public int getMaxIdleTime() {
                return 0;
            }

            @Override
            public boolean getEnabled() {
                return false;
            }

            @Override
            public String getHomeDirectory() {
                return System.getProperty("user.home");
            }
        });
    }

    public FtpDataType getDataType() {
        return dataType;
    }

    public void setDataType(FtpDataType dataType) {
        this.dataType = dataType;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public FtpFile getRenameFrom() {
        return renameFrom;
    }

    public void setRenameFrom(FtpFile renameFrom) {
        this.renameFrom = renameFrom;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public DataConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public FileSystemView getFileSystemView() {
        return fileSystemView;
    }

    public ServerSession getServerSession() {
        return serverSession;
    }

    /**
     * 重置会话
     */
    public void reset() {
        chunkSize = 0L;
    }
}
