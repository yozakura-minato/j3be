package com.yozakuraMinato.j3be.common.exception.custom;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message, null, false, false);
    }
}
