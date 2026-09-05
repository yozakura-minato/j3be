package com.yozakuraMinato.j3be.page.api.request;

/**
 * @param host String
 * @param page String
 */
public record GetPageByPathRequest(
        String host,
        String page
) {
}
