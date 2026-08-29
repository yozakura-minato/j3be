package com.yozakuraMinato.j3be.page.api.dto;

import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;

public record GetPageResponse(
        String hostPath,
        PageProfile pageProfile,
        Object pageContents
) {
}