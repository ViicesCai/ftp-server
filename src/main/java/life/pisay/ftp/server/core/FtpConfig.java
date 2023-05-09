package life.pisay.ftp.server.core;

import lombok.Data;

/**
 * FTP 配置
 *
 * @author Viices Cai
 * @time 2023/4/22
 */
@Data
public class FtpConfig {

    /**
     * 根目录
     */
    private String rootDir;

    /**
     * 实例对象
     */
    private volatile static FtpConfig instance;

    public static FtpConfig getInstance() {

        if (instance == null) {

            synchronized (FtpConfig.class) {

                if (instance == null) {
                    instance = new FtpConfig();
                }
            }
        }

        return instance;
    }

    private FtpConfig() { }
}
