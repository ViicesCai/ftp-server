package life.pisay.ftp.server.io.file.listing;

import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.utils.DateUtils;

/**
 * 根据 MS-DOS 规范格式化文件
 * 
 * @author Viices Cai
 * @time 2021/12/08
 */
public class MSListFormatter implements FileFormatter {

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

        builder.append(getLastModified(file));
        builder.append(BLANK);
        builder.append(getLength(file));
        builder.append(BLANK);
        builder.append(file.getName());
        builder.append(NEWLINE);

        return builder.toString();
    }

    /**
     * 获取文件大小
     *
     * @param file FTP 文件
     * @return 文件的大小(0 为目录)
     */
    private String getLength(FtpFile file) {
        String initStr = "                    ";
        long size = 0L;

        if (file.isFile()) {
            size = file.getSize();
            
            String sizeStr = String.valueOf(size);

            // 防止存在空文件
            if (sizeStr.length() > initStr.length()) {

                return sizeStr;
            }

            return initStr.substring(0, initStr.length() - sizeStr.length()) + sizeStr;
        
        } else {
        	
        	return "      <DIR>         ";
        }
    }

    /**
     * 获取最后修改时间
     *
     * @param file FTP 文件
     * @return Unix格式的时间
     */
    private String getLastModified(FtpFile file) {

        return DateUtils.getWindowDate(file.getLastModified());
    }
}
