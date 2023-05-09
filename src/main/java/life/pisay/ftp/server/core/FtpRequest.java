package life.pisay.ftp.server.core;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * FTP 请求
 *
 * @author Viices Cai
 * @time 2021/10/28
 */
@Getter
@NoArgsConstructor
public class FtpRequest {

    /**
     * 命令行
     */
    private String line;

    /**
     * 指令
     */
    private String command;

    /**
     * 参数
     */
    private String args;

    /**
     * 接收时间
     */
    private Long receivedTime;

    public FtpRequest(String line) {
        this.receivedTime = System.currentTimeMillis();
        this.line = line.trim();
        int index = line.indexOf(' ');
        this.command = parseCommand(line, index);
        this.args = parseArgs(line, index);
    }

    /**
     * 解析指令
     *
     * @param line 命令行
     * @param index 分隔索引
     * @return 指令
     */
    private String parseCommand(String line, Integer index) {
        String cmd;

        // 命令行包含参数
        if (index != -1) {
            cmd = line.substring(0, index).toUpperCase();

        } else {
            cmd = line.toUpperCase();
        }

        return cmd;
    }

    /**
     * 解析参数
     *
     * @param line 命令行
     * @param index 分隔索引
     * @return 指令
     */
    private String parseArgs(String line, Integer index) {
        String args = null;

        if (index != -1) {
            args = line.substring(index + 1);

            // 无参数
            if ("".equals(args)) {
                args = null;
            }
        }

        return args;
    }
}
