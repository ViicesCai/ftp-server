package life.pisay.ftp.server.io.file.listing;

import life.pisay.ftp.server.io.file.FtpFile;

/**
 * 根据 NLST 规范格式化文件
 *
 * @author Viices Cai
 * @time 2021/11/19
 */
public class NLSTFileFormat implements FileFormatter {

    /**
     * 换行符
     */
    private final static char[] NEWLINE = { '\r', '\n' };

    /**
     * 格式化文件
     *
     * @param file ftp 文件
     * @return 格式化后的文件信息
     */
    @Override
    public String format(FtpFile file) {
        StringBuilder builder = new StringBuilder();
        builder.append(file.getName());
        builder.append(NEWLINE);

        return builder.toString();
    }
}
