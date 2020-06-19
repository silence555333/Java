package com.data.lineage.exception;

/**
 * @author yangfei
 * @create 2020-06-19 14:44
 */
public class DBException extends RuntimeException {
    private static final long serialVersionUID = -5588025121452725145L;

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}
