package com.yozakuraMinato.j3be.common.exception.custom;

public class ResourceAccessDeniedException extends RuntimeException {
    public ResourceAccessDeniedException(String message) {
        super(message);
    }
}