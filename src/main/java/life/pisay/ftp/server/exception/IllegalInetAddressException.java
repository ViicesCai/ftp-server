package life.pisay.ftp.server.exception;

/**
 * 非法IP地址异常
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public class IllegalInetAddressException extends IllegalArgumentException {

    public IllegalInetAddressException() {
        super();
    }

    public IllegalInetAddressException(String s) {
        super(s);
    }
}
