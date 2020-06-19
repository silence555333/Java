package com.data.lineage.exception;

/**
 * @author yangfei
 * @create 2020-06-19 14:26
 */
public class SQLParseException  extends RuntimeException{
    private static final long serialVersionUID = -5588025121452725145L;

    public SQLParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLParseException(String message) {
        super(message);
    }

    public SQLParseException(Throwable cause) {
        super(cause);
    }
}
