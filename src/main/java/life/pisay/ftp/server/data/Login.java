package life.pisay.ftp.server.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录者
 *
 * @author Viices Cai
 * @time 2021/11/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 是否登录
     */
    private boolean isLoggedIn = false;
}
