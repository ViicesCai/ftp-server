package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.core.FtpConnection;
import life.pisay.ftp.server.enums.FtpReply;
import life.pisay.ftp.server.utils.StringUtils;

import java.io.IOException;

/**
 * 返回服务器支持的扩展命令
 *
 * @author Viices Cai
 * @time 2021/12/27
 */
public class FEAT implements Command {

    @Override
    public String name() {
        return "FEAT";
    }

    @Override
    public Boolean execute(FtpConnection connection, String args) throws IOException {

        connection.getFtpSession().reset();

        // 响应格式参考 Windows-Ftp-Server : 暴露 FTP 服务实现的特性指令
        StringBuilder data = new StringBuilder();
        data.append(StringUtils.formatLine("211-Extended features supported:"));
        data.append(StringUtils.formatLine(" LANG EN;CN*"));
        data.append(StringUtils.formatLine(" PBSZ"));
        data.append(StringUtils.formatLine(" PORT C;P;"));
        data.append(StringUtils.formatLine(" CCC"));
        data.append(StringUtils.formatLine(" HOST"));
        data.append(StringUtils.formatLine(" SIZE"));
        data.append(StringUtils.formatLine(" MDTM"));
        data.append(StringUtils.formatLine(" REST STREAM"));

        // 所以响应码不显式的出现，建议通过 FtpReply 枚举值
        data.append(
                StringUtils.contact(String.valueOf(FtpReply.REPLY_211.getCode()), " END"));

        connection.send(data.toString());
        return true;
    }
}
