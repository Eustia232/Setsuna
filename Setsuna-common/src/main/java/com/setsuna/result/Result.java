package com.setsuna.result;

import com.setsuna.constants.CodeConstant;

public class Result<T> {

    public int code;

    public String message;

    public T data;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public Result(int code, T data) {
        this.code = code;
        this.message = null;
        this.data = data;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CodeConstant.SUCCESS_CODE, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(CodeConstant.SUCCESS_CODE, message, data);
    }

    public static Result<Void> error(String message) {
        return new Result<>(CodeConstant.ERROR_CODE, message);
    }

}
