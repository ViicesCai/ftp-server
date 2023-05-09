package life.pisay.ftp.server.io.file.listing;

import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.utils.DateUtils;

import java.util.Arrays;

/**
 * UNIX 文件列表格式
 *
 * @author CAI
 * @time 2021/11/8
 */
public class LISTFileFormatter implements FileFormatter {

    /**
     * 间隔符
     */
    private final static char BLANK = ' ';

    /**
     * 换行符
     */
    private final static char[] NEWLINE = { '\r', '\n' };

    /**
     * 格式化文件
     *
     * @param file ftp 文件
     * @return 格式化后的文件信息(UNIX 文件列表格式)
     */
    @Override
    public String format(FtpFile file) {
        StringBuilder builder = new StringBuilder();

        builder.append(getPermission(file));
        builder.append(BLANK * 3);
        builder.append(file.getLinkCount());
        builder.append(BLANK);
        builder.append(file.getOwnerName());
        builder.append(BLANK);
        builder.append(file.getGroupName());
        builder.append(BLANK);
        builder.append(getLength(file));
        builder.append(BLANK);
        builder.append(getLastModified(file));
        builder.append(BLANK);
        builder.append(file.getName());
        builder.append(NEWLINE);

        return builder.toString();
    }

    /**
     * 获取文件权限符
     *
     * @param file FTP 文件
     * @return 文件的操作权限
     */
    private char[] getPermission(FtpFile file) {
        char[] perms = new char[10];

        // 使用 - 填充数组
        Arrays.fill(perms, '-');

        perms[0] = file.isDirectory() ? 'd' : '-';
        perms[1] = file.isReadable() ? 'r' : '-';
        perms[2] = file.isWritable() ? 'w' : '-';
        perms[3] = file.isDirectory() ? 'x' : '-';

        return perms;
    }

    /**
     * 获取文件大小
     *
     * @param file FTP 文件
     * @return 文件的大小(0 为目录)
     */
    private String getLength(FtpFile file) {
        String initStr = "            ";
        long size = 0L;

        if (file.isFile()) {
            size = file.getSize();
        }

        String sizeStr = String.valueOf(size);

        // 防止存在空文件
        if (sizeStr.length() > initStr.length()) {

            return sizeStr;
        }

        return initStr.substring(0, initStr.length() - sizeStr.length()) + sizeStr;
    }

    /**
     * 获取最后修改时间
     *
     * @param file FTP 文件
     * @return Unix格式的时间
     */
    private String getLastModified(FtpFile file) {

        return DateUtils.getUnixDate(file.getLastModified());
    }
}
