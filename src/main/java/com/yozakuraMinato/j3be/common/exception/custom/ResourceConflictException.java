package com.yozakuraMinato.j3be.common.exception.custom;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message, null, false, false);
    }
}
