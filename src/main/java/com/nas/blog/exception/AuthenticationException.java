package com.nas.blog.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

    private final ExceptionCode error;

    public AuthenticationException(ExceptionCode error) {
        super("FAIL_LOGIN errorCode: " + error.getCode());
        this.error = error;
    }
}
