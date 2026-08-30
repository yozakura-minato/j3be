package com.yozakuraMinato.j3be.page.api.dto;

import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;

import java.util.List;

public record GetAllPagesResponse(
        String hostPath,
        List<PageProfile> pageProfiles,
        Object fistPageContents
) {
}