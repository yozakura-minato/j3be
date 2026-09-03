package com.yozakuraMinato.j3be.page.service.implement;

import com.yozakuraMinato.j3be.common.exception.custom.ResourceAccessDeniedException;
import com.yozakuraMinato.j3be.common.exception.custom.ResourceConflictException;
import com.yozakuraMinato.j3be.common.exception.custom.ResourceNotFoundException;
import com.yozakuraMinato.j3be.page.api.dto.*;
import com.yozakuraMinato.j3be.page.model.Page;
import com.yozakuraMinato.j3be.page.model.type.PageAccess;
import com.yozakuraMinato.j3be.page.repository.PageRepository;
import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;
import com.yozakuraMinato.j3be.page.service.PageApiService;
import com.yozakuraMinato.j3be.page.util.PageMapper;
import com.yozakuraMinato.j3be.page.util.PageMessage;
import com.yozakuraMinato.j3be.user.service.UserModuleService;
import com.yozakuraMinato.j3be.user.util.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PageServiceImplement implements PageApiService {
    private final PageRepository pageRepository;
    private final PageMapper pageMapper;
    private final UserModuleService userModuleService;

    private static final String HOST_PATH = "system";
    private static final List<String> CONTENT_LIST = List.of(
            "Test page content 1",
            "Test page content 2",
            "Test page content 3",
            "Test page content 4",
            "Test page content 5",
            "Test page content 6"
    );

    @Transactional
    @Override
    public void createPage(CreatePageRequest request, UUID userId) {
        boolean isDisplayPathConflicts = pageRepository.existsByDisplayPath(request.displayPath());
        if (isDisplayPathConflicts) {
            throw new ResourceConflictException(PageMessage.DisplayPath.CONFLICT);
        }

        Page newPage = pageMapper.createPageRequestDtoToEntity(request);
        newPage.setUserId(userId);
        newPage.setCreatedAt(Instant.now());
        newPage.setCreatedBy(userId);

        pageRepository.save(newPage);
    }

    @Override
    public GetPageResponse getPageById(UUID pageId, UUID userId) {
        PageProfile pageProfile = pageRepository.getPageProfileByIdAndUserId(pageId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(PageMessage.Id.NOT_FOUND));

        return new GetPageResponse(HOST_PATH, pageProfile, CONTENT_LIST);
    }

    @Override
    public GetAllPagesResponse getAllPages(UUID userId) {
        List<PageProfile> pageProfiles = pageRepository.getAllPagesByUserId(userId);

        return new GetAllPagesResponse(HOST_PATH, pageProfiles, CONTENT_LIST);
    }

    @Override
    public GetPageResponse getPageByPath(GetPageByPathRequest request) {
        UUID hostId = userModuleService.getHostIdByHostPath(request.host())
                .orElseThrow(() -> new ResourceNotFoundException(UserMessage.DisplayPath.NOT_FOUND));

        PageProfile pageProfile = pageRepository.getPageProfileByDisplayPathAndUserId(request.page(), hostId)
                .orElseThrow(() -> new ResourceNotFoundException(PageMessage.DisplayPath.NOT_FOUND));

        if (!PageAccess.PUBLIC.equals(pageProfile.access())) {
            throw new ResourceAccessDeniedException(PageMessage.Access.DENIED);
        }

        return new GetPageResponse(request.host(), pageProfile, CONTENT_LIST);
    }

    @Transactional
    @Override
    public void updatePage(UUID pageId, UpdatePageRequest request, UUID userId) {
        Page existsPage = pageRepository.getPageByIdAndUserId(pageId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(PageMessage.Id.NOT_FOUND));

        pageMapper.updatePageFromUpdatePageRequest(request, existsPage);
        existsPage.setUpdatedAt(Instant.now());
        existsPage.setUpdatedBy(userId);
    }
}