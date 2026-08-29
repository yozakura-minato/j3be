package com.yozakuraMinato.j3be.common.exception;

import com.yozakuraMinato.j3be.common.dto.ApiResponse;
import com.yozakuraMinato.j3be.common.exception.custom.BusinessRuleException;
import com.yozakuraMinato.j3be.common.exception.custom.ResourceConflictException;
import com.yozakuraMinato.j3be.common.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(exception = ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(exception = ResourceConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceConflictException(ResourceConflictException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(exception = BusinessRuleException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessRuleException(BusinessRuleException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(exception = RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("common.badRequest"));
    }
}