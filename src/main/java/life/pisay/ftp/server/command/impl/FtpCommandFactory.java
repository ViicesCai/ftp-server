package life.pisay.ftp.server.command.impl;

import life.pisay.ftp.server.command.Command;
import life.pisay.ftp.server.command.CommandFactory;
import life.pisay.ftp.server.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * FTP 指令工厂
 *
 * @author Viices Cai
 * @time 2021/10/28
 */
public class FtpCommandFactory implements CommandFactory {

    /**
     * 指令集
     */
    private final Map<String, Command> commands;

    public FtpCommandFactory() {
        this(new HashMap<>());
    }

    public FtpCommandFactory(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * 获取指令
     *
     * @param commandName 指令名称
     * @return 指令对象
     */
    @Override
    public Command getCommand(String commandName) {
        if (StringUtils.isNull(commandName)) {
            return null;
        }

        return  commands.get(commandName.toUpperCase());
    }
}
