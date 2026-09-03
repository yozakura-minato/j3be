package com.yozakuraMinato.j3be.page.service;

import com.yozakuraMinato.j3be.page.api.dto.*;

import java.util.UUID;

public interface PageApiService {
    void createPage(CreatePageRequest request, UUID userId);

    GetPageResponse getPageById(UUID pageId, UUID userId);

    GetAllPagesResponse getAllPages(UUID userId);

    // TODO: Rewrite this method using view repository
    GetPageResponse getPageByPath(GetPageByPathRequest request);

    void updatePage(UUID pageId, UpdatePageRequest request, UUID userId);
}
