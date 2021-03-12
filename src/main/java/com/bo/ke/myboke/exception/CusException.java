package com.bo.ke.myboke.exception;

/**
 * 自定义异常类
 */
public class CusException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected int code;

    public CusException() {
    }

    public CusException(String message) {
        super(message);
    }

    public CusException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
