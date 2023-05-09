package life.pisay.ftp.server.utils;

import java.io.File;

/**
 * 文件工具类
 *
 * @author Viices Cai
 * @time 2023/5/9
 */
public class FileUtils {

    public File getUserHome() {

        return new File(System.getProperty("user.home"));
    }

    public static void main(String[] args) {

        File file = new File(System.getProperty("user.home")).getParentFile();
        System.out.println(file.getAbsolutePath());
    }

    private FileUtils() { }
}
