package com.yozakuraMinato.j3be.page.api.dto;

import com.yozakuraMinato.j3be.common.util.MockValues;
import com.yozakuraMinato.j3be.page.model.type.PageAccess;

public record CreatePageRequest(
        String displayPath,
        String title,
        String description,
        PageAccess access
) {
    public CreatePageRequest() {
        this(
                MockValues.OF_STRING,
                MockValues.OF_STRING,
                MockValues.OF_STRING,
                MockValues.OfPage.ACCESS
        );
    }
}