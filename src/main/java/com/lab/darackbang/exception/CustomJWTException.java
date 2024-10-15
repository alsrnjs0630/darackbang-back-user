package com.lab.darackbang.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomJWTException extends RuntimeException{

    public CustomJWTException(String message) {
        super(message);
    }

    public CustomJWTException(String message, Throwable cause) {
        super(message, cause);
    }
}

