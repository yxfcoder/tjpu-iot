package com.tjpu.iot.exception;

public class BusinessException extends Exception {

    private String message;

    private Integer code;

    public BusinessException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return this.code;
    }
}
