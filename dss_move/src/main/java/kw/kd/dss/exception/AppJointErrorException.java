package kw.kd.dss.exception;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 10:42
 */
public class AppJointErrorException  extends ErrorException{

    public AppJointErrorException(int errCode, String desc) {
        super(errCode, desc);
    }

    public AppJointErrorException(int errCode, String desc, Throwable cause) {
        super(errCode, desc);
        initCause(cause);
    }
}
