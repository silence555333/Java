package com.data.lineage.exception;

/**
 * @author yangfei
 * 不支持解析异常
 * @create 2020-06-19 17:06
 */
public class UnSupportedException  extends RuntimeException{
    private static final long serialVersionUID = -5588025121452725145L;

    public UnSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnSupportedException(String message) {
        super(message);
    }

    public UnSupportedException(Throwable cause) {
        super(cause);
    }
}
