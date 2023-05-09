package life.pisay.ftp.server.io.file.impl;

import life.pisay.ftp.server.exception.FtpException;
import life.pisay.ftp.server.io.file.FileSystemFactory;
import life.pisay.ftp.server.io.file.FileSystemView;
import life.pisay.ftp.server.user.User;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 本地文件系统工厂
 *
 * @author Viices Cai
 * @time 2023/5/3
 */
@Slf4j
public class NativeFileSystemFactory implements FileSystemFactory {

    /**
     * 是否创建主目录
     */
    private boolean createHome;

    public boolean isCreateHome() {
        return createHome;
    }

    public void setCreateHome(boolean createHome) {
        this.createHome = createHome;
    }

    @Override
    public FileSystemView createFileSystemView(User user) throws FtpException {

        synchronized (user) {

            if (this.createHome) {
                String home = user.getHomeDirectory();

                File homeDir = new File(home);
                if (homeDir.isFile()) {
                    log.warn("Not a directory :: {}", home);
                    throw new FtpException("Not a directory :: " + home);
                }

                if ((!homeDir.exists()) && (!homeDir.mkdirs())) {
                    log.warn("Cannot created user home :: {}", home);
                    throw new FtpException("Cannot created user home :: " + home);
                }
            }

            return new NativeFileSystemView(user);
        }
    }
}
