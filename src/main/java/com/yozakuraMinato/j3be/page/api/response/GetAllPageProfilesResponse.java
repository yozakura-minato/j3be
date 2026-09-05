package com.yozakuraMinato.j3be.page.api.response;

import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;

import java.util.List;

/**
 * @param hostPath     String
 * @param pageProfiles PageProfile List
 */
public record GetAllPageProfilesResponse(
        String hostPath,
        List<PageProfile> pageProfiles
) {
}
