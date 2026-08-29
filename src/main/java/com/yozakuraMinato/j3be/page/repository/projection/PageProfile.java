package com.yozakuraMinato.j3be.page.repository.projection;

import com.yozakuraMinato.j3be.common.util.MockValues;
import com.yozakuraMinato.j3be.page.model.type.PageAccess;

import java.util.UUID;

public record PageProfile(
        UUID id,
        String displayPath,
        String title,
        String description,
        PageAccess access
) {
    public PageProfile() {
        this(
                MockValues.OF_UUID,
                MockValues.OF_STRING,
                MockValues.OF_STRING,
                MockValues.OF_STRING,
                MockValues.OfPage.ACCESS
        );
    }
}