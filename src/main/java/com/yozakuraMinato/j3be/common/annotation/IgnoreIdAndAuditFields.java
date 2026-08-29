package com.yozakuraMinato.j3be.common.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.CLASS)
@Target(value = ElementType.METHOD)
@Mapping(target = "id", ignore = true)
@Mapping(target = "userId", ignore = true)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "updatedAt", ignore = true)
@Mapping(target = "updatedBy", ignore = true)
@Mapping(target = "deleted", ignore = true)
public @interface IgnoreIdAndAuditFields {
}