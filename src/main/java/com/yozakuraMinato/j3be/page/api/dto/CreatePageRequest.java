package com.yozakuraMinato.j3be.page.api.dto;

import com.yozakuraMinato.j3be.page.model.type.PageAccess;

public record CreatePageRequest(
        String displayPath,
        String title,
        String description,
        PageAccess access
) {
}
