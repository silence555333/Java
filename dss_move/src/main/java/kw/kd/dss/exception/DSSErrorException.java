package kw.kd.dss.exception;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 10:35
 */
public class DSSErrorException extends ErrorException {

    public DSSErrorException(int errCode, String desc) {
        super(errCode, desc);
    }

    public DSSErrorException(int errCode, String desc, String ip, int port, String serviceKind) {
        super(errCode, desc, ip, port, serviceKind);
    }

}
