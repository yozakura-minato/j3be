package com.yozakuraMinato.j3be.page.api;

import com.yozakuraMinato.j3be.common.dto.ApiResponse;
import com.yozakuraMinato.j3be.page.api.dto.CreatePageRequest;
import com.yozakuraMinato.j3be.page.api.dto.GetAllPagesResponse;
import com.yozakuraMinato.j3be.page.api.dto.GetPageByPathRequest;
import com.yozakuraMinato.j3be.page.api.dto.GetPageResponse;
import com.yozakuraMinato.j3be.page.service.PageApiService;
import com.yozakuraMinato.j3be.user.util.UserConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PageController {
    private final PageApiService pageApiService;

    @PostMapping(path = "/api/v1/pages")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createPage(@RequestBody CreatePageRequest request) {
        pageApiService.createPage(request, UserConstant.Temporary.USER_ID);
    }

    @GetMapping(path = "/api/v1/pages/{pageId}")
    public ResponseEntity<ApiResponse<GetPageResponse>> getPageById(@PathVariable UUID pageId) {
        GetPageResponse response = pageApiService.getPageById(pageId, UserConstant.Temporary.USER_ID);
        return ResponseEntity.ok(ApiResponse.data(response));
    }

    @GetMapping(path = "/api/v1/pages/all")
    public ResponseEntity<ApiResponse<GetAllPagesResponse>> getAllPages() {
        GetAllPagesResponse response = pageApiService.getAllPages(UserConstant.Temporary.USER_ID);
        return ResponseEntity.ok(ApiResponse.data(response));
    }

    // Public API
    @GetMapping(path = "/api/v1/pages")
    public ResponseEntity<ApiResponse<GetPageResponse>> getPageByPath(@ModelAttribute GetPageByPathRequest request) {
        GetPageResponse response = pageApiService.getPageByPath(request);
        return ResponseEntity.ok(ApiResponse.data(response));
    }
}