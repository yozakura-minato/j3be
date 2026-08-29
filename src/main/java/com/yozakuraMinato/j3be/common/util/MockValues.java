package com.yozakuraMinato.j3be.common.util;

import com.yozakuraMinato.j3be.page.model.type.PageAccess;

import java.util.UUID;

public class MockValues {
    public static final String OF_STRING = "Test string";
    public static final UUID OF_UUID = UUID.fromString("12345678-1234-1234-1234-123456789012");

    public static class OfPage {
        public static final PageAccess ACCESS = PageAccess.PUBLIC;
    }
}