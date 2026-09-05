package com.yozakuraMinato.j3be.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message, null, false, false);
    }
}
