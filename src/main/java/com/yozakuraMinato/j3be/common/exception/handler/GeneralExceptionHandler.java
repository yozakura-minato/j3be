package com.yozakuraMinato.j3be.common.exception.handler;

import com.yozakuraMinato.j3be.common.dto.ApiResponse;
import com.yozakuraMinato.j3be.common.exception.BusinessRuleException;
import com.yozakuraMinato.j3be.common.exception.ResourceConflictException;
import com.yozakuraMinato.j3be.common.exception.ResourceNotFoundException;
import com.yozakuraMinato.j3be.common.util.CommonMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    @ExceptionHandler(exception = ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error("ResourceNotFoundException with message: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(exception = ResourceConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceConflictException(ResourceConflictException exception) {
        log.error("ResourceConflictException with message: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(exception = BusinessRuleException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessRuleException(BusinessRuleException exception) {
        log.error("BusinessRuleException with message: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(exception = RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException exception) {
        Throwable cause = exception.getCause();
        if (cause == null) {
            log.error("{} with message: {}", exception.getClass().getSimpleName(), exception.getMessage());
        } else {
            log.error(
                    "{} caused by {} with message: {}",
                    exception.getClass().getSimpleName(), cause.getClass().getSimpleName(), cause.getMessage()
            );
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(CommonMessage.INTERNAL_SERVER_ERROR));
    }
}
