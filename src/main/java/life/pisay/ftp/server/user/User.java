package life.pisay.ftp.server.user;

/**
 * 用户接口
 *
 * @author Viices Cai
 * @time 2023/5/1
 */
public interface User {

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    String getName();

    /**
     * 获取密码
     *
     * @return 用户密码
     */
    String getPassword();

    /**
     * 获取最大空闲时间
     *
     * @return 最大空闲时间(s)
     */
    int getMaxIdleTime();

    /**
     * 获取用户启用状态
     *
     * @return ture:启用
     */
    boolean getEnabled();

    /**
     * 获取主目录
     *
     * @return 用户主目录路径
     */
    String getHomeDirectory();
}
