package com.yozakuraMinato.j3be.page.api;

import com.yozakuraMinato.j3be.common.dto.ApiResponse;
import com.yozakuraMinato.j3be.page.api.dto.*;
import com.yozakuraMinato.j3be.page.service.PageApiService;
import com.yozakuraMinato.j3be.user.util.UserConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/pages")
@RequiredArgsConstructor
public class PageController {
    private final PageApiService pageApiService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createPage(@RequestBody CreatePageRequest request) {
        UUID userId = UserConstant.Temporary.USER_ID;
        pageApiService.createPage(request, userId);
    }

    @GetMapping(path = "/{pageId}")
    public ResponseEntity<ApiResponse<GetPageResponse>> getPageById(@PathVariable UUID pageId) {
        UUID userId = UserConstant.Temporary.USER_ID;
        GetPageResponse response = pageApiService.getPageById(pageId, userId);
        return ResponseEntity.ok(ApiResponse.data(response));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse<GetAllPagesResponse>> getAllPages() {
        UUID userId = UserConstant.Temporary.USER_ID;
        GetAllPagesResponse response = pageApiService.getAllPages(userId);
        return ResponseEntity.ok(ApiResponse.data(response));
    }

    // Public API
    @GetMapping()
    public ResponseEntity<ApiResponse<GetPageResponse>> getPageByPath(@ModelAttribute GetPageByPathRequest request) {
        GetPageResponse response = pageApiService.getPageByPath(request);
        return ResponseEntity.ok(ApiResponse.data(response));
    }

    @PostMapping(path = "/{pageId}")
    public void updatePage(@PathVariable UUID pageId, @RequestBody UpdatePageRequest request) {
        UUID userId = UserConstant.Temporary.USER_ID;
        pageApiService.updatePage(pageId, request, userId);
    }
}