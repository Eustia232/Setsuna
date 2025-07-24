package com.setsuna.handler;

import com.setsuna.exception.BaseException;
import com.setsuna.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Result<Void>> handlerBaseException(BaseException e) {
        log.error("自定义异常:{},异常原因:{}", e
                .getClass()
                .getName(), e.getMessage());
        log.debug("", e);
        return ResponseEntity
                .status(e.getCode())
                .body(Result.error(e.getMessage()));
    }

}
