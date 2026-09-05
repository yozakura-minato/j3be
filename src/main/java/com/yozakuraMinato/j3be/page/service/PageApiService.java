package com.yozakuraMinato.j3be.page.service;

import com.yozakuraMinato.j3be.page.api.request.CreatePageRequest;
import com.yozakuraMinato.j3be.page.api.request.GetAllPagesByPathRequest;
import com.yozakuraMinato.j3be.page.api.request.GetPageByPathRequest;
import com.yozakuraMinato.j3be.page.api.request.UpdatePageRequest;
import com.yozakuraMinato.j3be.page.api.response.GetAllPageProfilesResponse;
import com.yozakuraMinato.j3be.page.api.response.GetPageResponse;

import java.util.UUID;

public interface PageApiService {
    void createPage(CreatePageRequest request, UUID userId);

    GetPageResponse getPageById(UUID pageId, UUID userId);

    GetAllPageProfilesResponse getAllPages(UUID userId);

    GetPageResponse getPageByPath(GetPageByPathRequest request);

    GetAllPageProfilesResponse getAllPagesByPath(GetAllPagesByPathRequest request);

    void updatePage(UUID pageId, UpdatePageRequest request, UUID userId);

    void softDeletePage(UUID pageId, UUID userId);
}
