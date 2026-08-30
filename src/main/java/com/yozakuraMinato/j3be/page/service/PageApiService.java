package com.yozakuraMinato.j3be.page.service;

import com.yozakuraMinato.j3be.page.api.dto.CreatePageRequest;
import com.yozakuraMinato.j3be.page.api.dto.GetAllPagesResponse;
import com.yozakuraMinato.j3be.page.api.dto.GetPageByPathRequest;
import com.yozakuraMinato.j3be.page.api.dto.GetPageResponse;

import java.util.UUID;

public interface PageApiService {
    void createPage(CreatePageRequest request, UUID userId);

    GetPageResponse getPageById(UUID pageId, UUID userId);

    GetAllPagesResponse getAllPages(UUID userId);

    GetPageResponse getPageByPath(GetPageByPathRequest request);
}