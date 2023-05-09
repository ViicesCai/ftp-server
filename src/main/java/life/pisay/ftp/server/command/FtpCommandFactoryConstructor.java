package life.pisay.ftp.server.command;

import life.pisay.ftp.server.command.impl.FtpCommandFactory;
import life.pisay.ftp.server.command.impl.*;
import life.pisay.ftp.server.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Ftp 指令工厂构造器
 *
 * @author Viices Cai
 * @time 2023/4/22
 */
public class FtpCommandFactoryConstructor {

    /**
     * 默认指令集
     */
    private static final HashMap<String, Command> DEFAULT_COMMANDS = new HashMap<>();

    /**
     * 需鉴权的指令集
     */
    private static final HashMap<String, Command> AUTH_COMMAND = new HashMap<>();

    /**
     * 指令集
     */
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * 使用默认指令集
     */
    private Boolean useDefaultCommands = true;

    /**
     * 初始化默认指令集
     */
    static {
        DEFAULT_COMMANDS.put("USER", new USER());
        DEFAULT_COMMANDS.put("PASS", new PASS());
        DEFAULT_COMMANDS.put("PORT", new PORT());
        DEFAULT_COMMANDS.put("PWD", new PWD());
        DEFAULT_COMMANDS.put("TYPE", new TYPE());
        DEFAULT_COMMANDS.put("PASV", new PASV());
        DEFAULT_COMMANDS.put("LIST", new LIST());
        DEFAULT_COMMANDS.put("CWD", new CWD());
        DEFAULT_COMMANDS.put("RETR", new RETR());
        DEFAULT_COMMANDS.put("STOR", new STOR());
        DEFAULT_COMMANDS.put("MKD", new MKD());
        DEFAULT_COMMANDS.put("QUIT", new QUIT());
        DEFAULT_COMMANDS.put("CDUP", new CDUP());
        DEFAULT_COMMANDS.put("RNFR", new RNFR());
        DEFAULT_COMMANDS.put("RNTO", new RNTO());
        DEFAULT_COMMANDS.put("DELE", new DELE());
        DEFAULT_COMMANDS.put("RMD", new RMD());
        DEFAULT_COMMANDS.put("NOOP", new NOOP());
        DEFAULT_COMMANDS.put("SYST", new SYST());
        DEFAULT_COMMANDS.put("SIZE", new SIZE());
    }

    /**
     * 创建指令工厂
     *
     * @return 指令工厂
     */
    public CommandFactory createCommandFactory() {
        Map<String, Command> mergedCommands = new HashMap<>(commands);

        if(useDefaultCommands) {
            mergedCommands.putAll(DEFAULT_COMMANDS);
        }

        return new FtpCommandFactory(mergedCommands);
    }

    public Boolean getUseDefaultCommands() {
        return useDefaultCommands;
    }

    public void setUseDefaultCommands(Boolean useDefaultCommands) {
        this.useDefaultCommands = useDefaultCommands;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, Command> commands) {
        if (commands == null) {
            throw new NullPointerException("CommandMap can not be null");
        }

        this.commands.clear();

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            this.commands.put(entry.getKey().toUpperCase(), entry.getValue());
        }
    }

    /**
     * 添加指令
     *
     * @param commandName 指令名称
     * @param command 指令对象
     */
    public void addCommands(String commandName, Command command) {

        if (StringUtils.isNull(commandName)) {
            throw new NullPointerException("CommandName can not be null");
        }

        if (command == null) {
            throw new NullPointerException("Command can not be null");
        }

        commands.put(commandName.toUpperCase(), command);
    }
}
