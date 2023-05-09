package life.pisay.ftp.server.io.file;

import life.pisay.ftp.server.exception.FtpException;
import life.pisay.ftp.server.user.User;

/**
 * 文件系统工厂
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public interface FileSystemFactory {

    /**
     * 创建指定用户的文件系统视图
     *
     * @param user 用户
     * @return 文件系统视图
     * @throws FtpException FTP异常
     */
    FileSystemView createFileSystemView(User user) throws FtpException;
}
