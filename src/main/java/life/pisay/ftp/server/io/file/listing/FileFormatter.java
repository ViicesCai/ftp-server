package life.pisay.ftp.server.io.file.listing;

import life.pisay.ftp.server.io.file.FtpFile;

/**
 * 文件格式化接口
 *
 * @author Viices Cai
 * @time 2021/11/8
 */
public interface FileFormatter {

    /**
     * 格式化文件
     *
     * @param file ftp 文件
     * @return 格式化后的文件信息
     */
    String format(FtpFile file);
}
