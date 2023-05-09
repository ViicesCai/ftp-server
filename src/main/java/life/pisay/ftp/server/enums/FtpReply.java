package life.pisay.ftp.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FTP 响应码
 *
 * @author Viices Cai
 * @time 2021/10/27
 */
@AllArgsConstructor
@Getter
public enum FtpReply {

    /**
     * 重新开始标记响应：对于这种情况，文本应该是明确的，无需进行特殊实现；
     * 必须形如：MARK yyyy = mmmm；
     * yyyy 是用户进程数据流标记，mmmm 是服务器的等效标记（注意，标记间的空格和 "="）
     */
    REPLY_110(110, "Restart marker reply"),

    /**
     * 服务将在 {nnn} 分钟后准备完成
     */
    REPLY_120(120, "Service ready in {nnn} minutes."),

    /**
     * 数据连接已打开，传输开始
     */
    REPLY_125(125, "Data connection already open; transfer starting."),

    /**
     * 文件状态 OK，将打开数据连接
     */
    REPLY_150(150, "File status okay; about to open data connection."),

    /**
     * 命令 OK
     */
    REPLY_200(200, "{} Command okay."),

    /**
     * 命令没有实现，对本站冗余
     */
    REPLY_202(202, "Command not implemented, superfluous at this site."),

    /**
     * 系统状态，或者系统帮助响应
     */
    REPLY_211(211, "System status, or system help reply."),

    /**
     * 目录状态
     */
    REPLY_212(212, "Directory status."),

    /**
     * 文件状态
     */
    REPLY_213(213, "File status."),

    /**
     * 帮助信息：关于如何使用服务器，或者特殊的非标准的命令的意义
     */
    REPLY_214(214, "Help Message."),

    /**
     * 系统类型名称：这里的 NAME 指在 Assigned Numbers 文档中列出的正式名称
     */
    REPLY_215(215, "{NAME} system type."),

    /**
     * 服务准备接收新用户
     */
    REPLY_220(220, "Service ready for new user."),

    /**
     * 服务关闭控制连接：已注销
     */
    REPLY_221(221, "Service closing control connection."),

    /**
     * 数据连接打开，没有传输
     */
    REPLY_225(225, "Data connection open; no transfer in progress."),

    /**
     * 关闭数据连接：请求文件动作成功，如：文件传输或放弃
     */
    REPLY_226(226, "Closing data connection."),

    /**
     * 进入被动模式
     */
    REPLY_227(227, "Entering Passive Mode ({})."),

    /**
     * 用户登录成功，继续
     */
    REPLY_230(230, "User logged in, proceed."),

    /**
     * 请求文件动作 OK，完成
     */
    REPLY_250(250, "Requested file action okay, completed."),

    /**
     * {PATHNAME} 创建了
     */
    REPLY_257(257, "\"{PATHNAME}\" created."),

    /**
     * 用户名 OK，需要密码
     */
    REPLY_331(331, "User name okay, need password."),

    /**
     * 需要帐号才能登录
     */
    REPLY_332(332, "Need account for login."),

    /**
     * 请求文件动作需要进一步的信息
     */
    REPLY_350(350, "Requested file action pending further information."),

    /**
     * 服务不可用，关闭控制连接：如果服务器知道它必须关闭，应该以 421 作为任何命令的响应码
     */
    REPLY_421(421, "Service not available, closing control connection."),

    /**
     * 不能打开数据连接
     */
    REPLY_425(425, "Can't open data connection."),

    /**
     * 连接关闭，放弃传输
     */
    REPLY_426(426, "Connection closed; transfer aborted."),

    /**
     * 请求文件动作没有执行：文件不可使用，例：文件忙
     */
    REPLY_450(450, "Requested file action not taken."),

    /**
     * 请求动作放弃，处理中发生本地错误
     */
    REPLY_451(451, "Requested action aborted. Local error in processing."),

    /**
     * 请求动作未执行：系统存储空间不足
     */
    REPLY_452(452, "Requested action not taken."),

    /**
     * 语法错误，命令不能被识别
     */
    REPLY_500(500, "Syntax error, command unrecognized."),

    /**
     * 参数语法错误
     */
    REPLY_501(501, "Syntax error in parameters or arguments."),

    /**
     * 命令没有实现
     */
    REPLY_502(502, "Command not implemented."),

    /**
     * 命令顺序错误
     */
    REPLY_503(503, "Bad sequence of commands."),

    /**
     * 没有实现这个命令参数
     */
    REPLY_504(504, "Command not implemented for that parameter."),

    /**
     * 没有登录成功
     */
    REPLY_530(530, "Not logged in."),

    /**
     * 需要帐号来存储文件
     */
    REPLY_532(532, "Need account for storing files."),

    /**
     * 请求的动作没有执行：文件不可用，例：没有找到文件或没有文件的访问权限
     */
    REPLY_550(550, "Requested action not taken."),

    /**
     * 请求动作放弃，未知的页面类型
     */
    REPLY_551(551, "Requested action aborted: page type unknown."),

    /**
     * 请求文件动作被放弃：超出存储分配空间，例：当前的路径或数据集
     */
    REPLY_552(552, "Requested file action aborted."),

    /**
     * 请求动作未获得：文件名不允许
     */
    REPLY_553(553, "Requested action not taken.");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应信息
     */
    private final String info;

    public static FtpReply valueOf(int code) {
        for (FtpReply reply : FtpReply.values()) {
            if (reply.code == code) {
                return reply;
            }
        }

        throw new IllegalArgumentException("Unknown reply : " + code);
    }
}
