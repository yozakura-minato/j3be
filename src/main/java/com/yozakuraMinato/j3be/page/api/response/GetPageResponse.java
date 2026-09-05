package com.yozakuraMinato.j3be.page.api.response;

import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;

import java.util.List;

/**
 * @param hostPath     String
 * @param pageProfile  PageProfile
 * @param pageContents String List
 */
public record GetPageResponse(
        String hostPath,
        PageProfile pageProfile,
        List<String> pageContents
) {
}
