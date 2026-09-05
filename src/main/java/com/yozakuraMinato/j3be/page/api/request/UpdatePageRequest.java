package com.yozakuraMinato.j3be.page.api.request;

import com.yozakuraMinato.j3be.page.model.type.PageAccess;

/**
 * @param displayPath String
 * @param title       String
 * @param description String
 * @param access      PageAccess
 */
public record UpdatePageRequest(
        String displayPath,
        String title,
        String description,
        PageAccess access
) {
}
