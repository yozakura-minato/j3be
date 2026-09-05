package com.yozakuraMinato.j3be.page.service.implement;

import com.yozakuraMinato.j3be.common.exception.ResourceConflictException;
import com.yozakuraMinato.j3be.common.exception.ResourceNotFoundException;
import com.yozakuraMinato.j3be.page.api.request.CreatePageRequest;
import com.yozakuraMinato.j3be.page.api.request.GetAllPagesByPathRequest;
import com.yozakuraMinato.j3be.page.api.request.GetPageByPathRequest;
import com.yozakuraMinato.j3be.page.api.request.UpdatePageRequest;
import com.yozakuraMinato.j3be.page.api.response.GetAllPageProfilesResponse;
import com.yozakuraMinato.j3be.page.api.response.GetPageResponse;
import com.yozakuraMinato.j3be.page.model.Page;
import com.yozakuraMinato.j3be.page.model.type.PageAccess;
import com.yozakuraMinato.j3be.page.repository.PageRepository;
import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;
import com.yozakuraMinato.j3be.page.service.PageApiService;
import com.yozakuraMinato.j3be.page.util.PageMapper;
import com.yozakuraMinato.j3be.page.util.PageMessage;
import com.yozakuraMinato.j3be.user.service.UserModuleService;
import com.yozakuraMinato.j3be.user.util.UserConstant;
import com.yozakuraMinato.j3be.user.util.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PageServiceImplement implements PageApiService {
    private final PageRepository pageRepository;
    private final PageMapper pageMapper;
    private final UserModuleService userModuleService;

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

        return new GetPageResponse(UserConstant.Temporary.HOST_PATH, pageProfile, CONTENT_LIST);
    }

    @Override
    public GetAllPageProfilesResponse getAllPages(UUID userId) {
        List<PageProfile> pageProfiles = pageRepository.getAllPagesByUserId(userId);

        return new GetAllPageProfilesResponse(UserConstant.Temporary.HOST_PATH, pageProfiles);
    }

    @Override
    public GetPageResponse getPageByPath(GetPageByPathRequest request) {
        UUID hostId = userModuleService.getHostIdByHostPath(request.host())
                .orElseThrow(() -> new ResourceNotFoundException(UserMessage.DisplayPath.NOT_FOUND));

        PageProfile pageProfile = pageRepository.getPageProfileByDisplayPathAndAccessAndUserId(
                request.page(), PageAccess.PUBLIC, hostId
        ).orElseThrow(() -> new ResourceNotFoundException(PageMessage.DisplayPath.NOT_FOUND));

        return new GetPageResponse(request.host(), pageProfile, CONTENT_LIST);
    }

    @Override
    public GetAllPageProfilesResponse getAllPagesByPath(GetAllPagesByPathRequest request) {
        UUID hostId = userModuleService.getHostIdByHostPath(request.host())
                .orElseThrow(() -> new ResourceNotFoundException(UserMessage.DisplayPath.NOT_FOUND));

        List<PageProfile> pageProfiles = pageRepository.getAllPageProfilesByAccessAndUserId(PageAccess.PUBLIC, hostId);

        return new GetAllPageProfilesResponse(request.host(), pageProfiles);
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

    @Transactional
    @Override
    public void softDeletePage(UUID pageId, UUID userId) {
        int affectedPage = pageRepository.softDeletePage(pageId, userId, Instant.now(), userId);
        if (affectedPage == 0) {
            throw new ResourceNotFoundException(PageMessage.Id.NOT_FOUND);
        }
    }
}
