package com.codeit.springwebbasic.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CommonExceptionHandler {
    // Controller 단에서 발생하는 모든 예외를 일괄 처리하는 클래스
    // 실제 예외는 Service 계층에서 대부분 발생하지만, 따로 에외 처리가 없는 경우
    // 메서드를 호출한 상위 계층으로 전파됩니다.

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgsHandler(IllegalArgumentException e) {
        e.printStackTrace();
        // 예외의 원인을 http 상태 코드와 메세지를 통해 알려주고 싶다. -> ResponseEntity
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> illegalArgsHandler(IllegalStateException e) {
        e.printStackTrace();
        // 예외의 원인을 http 상태 코드와 메세지를 통해 알려주고 싶다. -> ResponseEntity
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 미처 준비하지 못한 타입의 예외가 발생했을 시 처리할 메서드
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();
        //1. 오류 결과를 담을 Map을 생성합니다. ( Key: 필드명, value: 에러 메세지)
        Map<String, String> errors = new HashMap<>();
/*
        // BindingResult: 오류 결과 보고서
        BindingResult bindingResult = e.getBindingResult();

        // BindingResult에서 @Valid에 실패한 필드 목록을 불러옵니다.
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (FieldError error : fieldErrors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);

        }
 */
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
