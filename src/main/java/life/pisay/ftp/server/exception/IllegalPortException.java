package life.pisay.ftp.server.exception;

/**
 * 非法IP地址异常
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public class IllegalPortException extends IllegalArgumentException {

    public IllegalPortException() {
        super();
    }

    public IllegalPortException(String s) {
        super(s);
    }
}
