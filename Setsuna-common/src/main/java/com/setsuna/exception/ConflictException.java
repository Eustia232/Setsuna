package com.setsuna.exception;

import jakarta.servlet.http.HttpServletResponse;

public class ConflictException extends BaseException {

    public ConflictException(String message) {
        super(HttpServletResponse.SC_CONFLICT, message);
    }

    public ConflictException(Throwable cause) {
        super(HttpServletResponse.SC_CONFLICT, cause);
    }

    public ConflictException(String message, Throwable cause) {
        super(HttpServletResponse.SC_CONFLICT, message, cause);
    }

}
