package com.yozakuraMinato.j3be.page.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageAccess {
    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    private final String description;
}