package com.yozakuraMinato.j3be.page.api.dto;

public record GetPageByPathRequest(
        String host,
        String page
) {
}