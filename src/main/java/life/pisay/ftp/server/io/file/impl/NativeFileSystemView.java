package life.pisay.ftp.server.io.file.impl;

import life.pisay.ftp.server.exception.FtpException;
import life.pisay.ftp.server.io.file.FileSystemView;
import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.user.User;
import life.pisay.ftp.server.utils.FileSystemUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 本地文件系统视图
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
@Slf4j
public class NativeFileSystemView implements FileSystemView {

    /**
     * 默认根路径
     */
    private static final String DEFAULT_ROOT_PATH = "/";

    /**
     * 根目录
     */
    private final String rootDir;

    /**
     * 当前目录
     */
    private String currDir;

    /**
     * 用户
     */
    private final User user;

    public NativeFileSystemView(User user) throws FtpException {

        if (user == null) {
            throw new IllegalArgumentException("User can not be null");
        }

        if (user.getHomeDirectory() == null) {
            throw new IllegalArgumentException("Usre Home directory can not be null");
        }

        // 格式化根目录
        String rootDir = user.getHomeDirectory();
        rootDir = FileSystemUtils.generalSeparator(rootDir);
        rootDir = FileSystemUtils.appendSlash(rootDir);

        log.debug("Native filesystem view created for user \"{}\" with root \"{}\"", user.getName(), rootDir);

        this.rootDir = rootDir;
        this.user = user;

        // 当前目录默认为根目录
        currDir = DEFAULT_ROOT_PATH;
    }

    /**
     * 获取主目录
     *
     * @return 用户的主目录
     */
    @Override
    public FtpFile getHomeDirectory() {

        return new NativeFtpFile(DEFAULT_ROOT_PATH, new File(rootDir), user);
    }

    /**
     * 获取当前操作的目录
     *
     * @return 用户当前的目录
     */
    @Override
    public FtpFile getWorkingDirectory() {
        FtpFile ftpFile;

        // 如果当前目录为根
        if (DEFAULT_ROOT_PATH.equals(currDir)) {
            ftpFile = new NativeFtpFile(DEFAULT_ROOT_PATH, new File(rootDir), user);

        } else {
            File file = new File(rootDir, currDir.substring(1));
            ftpFile = new NativeFtpFile(currDir, file, user);
        }

        return ftpFile;
    }

    /**
     * 获取文件对象
     *
     * @param file 要获取的文件路径
     * @return 文件对象
     */
    @Override
    public FtpFile getFile(String file) {

        // 文件实际名称
        String actualFileName = FileSystemUtils.getActualFileName(rootDir, currDir, file);

        File actualFile = new File(actualFileName);

        // 去除根路径
        String fileName = actualFileName.substring(rootDir.length() - 1);

        return new NativeFtpFile(fileName, actualFile, user);
    }

    /**
     * 改变当前目录
     *
     * @param dir 要设置为用户当前目录的目录路径
     * @return true:操作成功
     */
    @Override
    public boolean changeWorkingDirectory(String dir) {

        dir = FileSystemUtils.getActualFileName(rootDir, currDir, dir);

        File file = new File(dir);

        if (!file.isDirectory()) {
            return false;
        }

        dir = dir.substring(rootDir.length() - 1);
        if (dir.charAt(dir.length() - 1) != '/') {
            dir = dir + '/';
        }

        currDir = dir;
        return true;
    }

    /**
     * 文件系统是否支持随机访问
     *
     * @return true:支持
     */
    @Override
    public boolean isRandomAccessible() {

        return true;
    }
}
