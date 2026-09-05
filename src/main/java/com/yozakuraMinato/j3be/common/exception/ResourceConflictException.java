package com.yozakuraMinato.j3be.common.exception;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message, null, false, false);
    }
}
