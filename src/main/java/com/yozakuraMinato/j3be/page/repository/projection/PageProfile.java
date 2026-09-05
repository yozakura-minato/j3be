package com.yozakuraMinato.j3be.page.repository.projection;

import com.yozakuraMinato.j3be.page.model.type.PageAccess;

import java.util.UUID;

/**
 * @param id          UUID
 * @param displayPath String
 * @param title       String
 * @param description String
 * @param access      PageAccess
 */
public record PageProfile(
        UUID id,
        String displayPath,
        String title,
        String description,
        PageAccess access
) {
}
