package life.pisay.ftp.server.exception;

/**
 * FTP 异常
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public class FtpException extends RuntimeException {

    public FtpException() {
        super();
    }

    public FtpException(String msg) {
        super(msg);
    }
}
