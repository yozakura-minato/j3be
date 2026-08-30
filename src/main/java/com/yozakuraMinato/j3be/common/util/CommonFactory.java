package com.yozakuraMinato.j3be.common.util;

import org.apache.commons.lang3.RandomStringUtils;

public class CommonFactory {
    public static String string() {
        return RandomStringUtils.insecure().nextAlphanumeric(10, 20);
    }
}