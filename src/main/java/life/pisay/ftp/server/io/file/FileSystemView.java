package life.pisay.ftp.server.io.file;

/**
 * 文件系统视图
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public interface FileSystemView {

    /**
     * 获取主目录
     *
     * @return 用户的主目录
     */
    FtpFile getHomeDirectory();

    /**
     * 获取当前操作的目录
     *
     * @return 用户当前操作的目录
     */
    FtpFile getWorkingDirectory();

    /**
     * 获取文件对象
     *
     * @param file 要获取的文件路径
     * @return 文件对象
     */
    FtpFile getFile(String file);

    /**
     * 改变当前目录
     *
     * @param dir 要设置为用户当前目录的目录路径
     * @return true:操作成功
     */
    boolean changeWorkingDirectory(String dir);

    /**
     * 文件系统是否支持随机访问
     *
     * @return true:支持
     */
    boolean isRandomAccessible();
}
