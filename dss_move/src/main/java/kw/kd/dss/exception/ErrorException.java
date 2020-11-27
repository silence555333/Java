package kw.kd.dss.exception;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 10:38
 */
public class ErrorException extends DWCException {
    private ExceptionLevel level;

    public ErrorException(int errCode, String desc) {
        super(errCode, desc);
        this.level = ExceptionLevel.ERROR;
    }

    public ErrorException(int errCode, String desc, String ip, int port, String serviceKind) {
        super(errCode, desc, ip, port, serviceKind);
        this.level = ExceptionLevel.ERROR;
    }

    public ExceptionLevel getLevel() {
        return this.level;
    }

}
