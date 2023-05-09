package life.pisay.ftp.server.exception;

/**
 * 数据连接异常
 *
 * @author Viices Cai
 * @time 2021/11/8
 */
public class DataConnectionException extends RuntimeException {

    public DataConnectionException() {
        super();
    }

    public DataConnectionException(String msg) {
        super(msg);
    }

    public DataConnectionException(String msg, Throwable th) {
        super(msg, th);
    }
}
