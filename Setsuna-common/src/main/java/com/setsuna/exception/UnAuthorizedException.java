package com.setsuna.exception;

import jakarta.servlet.http.HttpServletResponse;

public class UnAuthorizedException extends BaseException {

    public UnAuthorizedException(String message, Throwable cause) {
        super(HttpServletResponse.SC_UNAUTHORIZED, message, cause);
    }

    public UnAuthorizedException(String message) {
        super(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    public UnAuthorizedException(Throwable cause) {
        super(HttpServletResponse.SC_UNAUTHORIZED, cause);
    }

}
