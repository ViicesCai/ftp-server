package life.pisay.ftp.server.command;

/**
 * 指令工厂
 *
 * @author Viices Cai
 * @time 2023/4/22
 */
public interface CommandFactory {

    /**
     * 获取指令
     *
     * @param commandName 指令名称
     * @return 指令对象
     */
    Command getCommand(String commandName);
}
