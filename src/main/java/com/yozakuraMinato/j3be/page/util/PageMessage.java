package com.yozakuraMinato.j3be.page.util;

public class PageMessage {
    private static final String PAGE = "page";

    public static class Id {
        private static final String ID = PAGE + ".id";

        public static final String NOT_FOUND = ID + ".notFound";
    }

    public static class DisplayPath {
        private static final String DISPLAY_PATH = PAGE + ".displayPath";

        public static final String CONFLICT = DISPLAY_PATH + ".conflict";
        public static final String NOT_FOUND = DISPLAY_PATH + ".notFound";
    }

    public static class Access {
        private static final String ACCESS = PAGE + ".access";

        public static final String DENIED = ACCESS + ".denied";
    }
}
