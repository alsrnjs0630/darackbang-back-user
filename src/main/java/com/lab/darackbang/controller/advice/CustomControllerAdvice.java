package com.lab.darackbang.controller.advice;

import com.lab.darackbang.exception.CustomJWTException;
import com.lab.darackbang.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomControllerAdvice {

    //컬렉션에서 요소를 가져오려고 할 때, 해당 요소가 없는 경우에 발생하는 예외
    @ExceptionHandler(CustomJWTException.class)
    protected ResponseEntity<?> handleCustomJWTException(CustomJWTException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","ERROR_ACCESSDENIED"));
    }

    // 유저 정보를 찾지 못했을 경우 예외 처리
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleCustomJWTException(UserNotFoundException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","ERROR_USER_NOT_FOUND"));
    }
}
