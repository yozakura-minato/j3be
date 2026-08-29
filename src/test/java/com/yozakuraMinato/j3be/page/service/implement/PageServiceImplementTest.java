package com.yozakuraMinato.j3be.page.service.implement;

import com.yozakuraMinato.j3be.common.exception.custom.ResourceConflictException;
import com.yozakuraMinato.j3be.common.exception.custom.ResourceNotFoundException;
import com.yozakuraMinato.j3be.page.api.dto.CreatePageRequest;
import com.yozakuraMinato.j3be.page.api.dto.GetPageByPathRequest;
import com.yozakuraMinato.j3be.page.model.Page;
import com.yozakuraMinato.j3be.page.repository.PageRepository;
import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;
import com.yozakuraMinato.j3be.page.util.PageMapper;
import com.yozakuraMinato.j3be.page.util.PageMessage;
import com.yozakuraMinato.j3be.user.service.UserModuleService;
import com.yozakuraMinato.j3be.user.util.UserMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageServiceImplementTest {
    @Mock
    private PageRepository pageRepository;
    @Mock
    private PageMapper pageMapper;
    @Mock
    private UserModuleService userModuleService;

    @InjectMocks
    private PageServiceImplement pageService;

    @Captor
    private ArgumentCaptor<Page> pageCaptor;

    @Nested
    @DisplayName(value = "Test for: createPage")
    class CreateOfPageTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD throw exception WHEN displayPath is conflicts")
        void createPage_DisplayPathConflict() {
            var mockRequest = new CreatePageRequest();
            var mockUserId = UUID.randomUUID();

            when(pageRepository.existsByDisplayPath(mockRequest.displayPath()))
                    .thenReturn(true);

            var exception = assertThrows(
                    ResourceConflictException.class,
                    () -> pageService.createPage(mockRequest, mockUserId)
            );
            assertEquals(PageMessage.DisplayPath.CONFLICT, exception.getMessage());

            verify(pageMapper, never()).createPageRequestDtoToEntity(any());
            verify(pageRepository, never()).save(any());
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD map and save WHEN request is valid")
        void createPage_Success() {
            var mockRequest = new CreatePageRequest();
            var mockUserId = UUID.randomUUID();

            when(pageRepository.existsByDisplayPath(mockRequest.displayPath()))
                    .thenReturn(false);

            var mockEntity = new Page();
            when(pageMapper.createPageRequestDtoToEntity(mockRequest))
                    .thenReturn(mockEntity);

            pageService.createPage(mockRequest, mockUserId);

            verify(pageRepository, times(1)).save(pageCaptor.capture());

            var savedPage = pageCaptor.getValue();
            assertEquals(mockUserId, savedPage.getUserId());
            assertNotNull(savedPage.getCreatedAt());
            assertEquals(mockUserId, savedPage.getCreatedBy());
        }
    }

    @Nested
    @DisplayName(value = "Test for: getPageById")
    class GetOfPageByIdTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD throw exception WHEN page ID not found")
        void getPageById_PageIdNotFound() {
            var mockPageId = UUID.randomUUID();
            var mockUserId = UUID.randomUUID();

            when(pageRepository.getPageProfileByIdAndUserId(mockPageId, mockUserId))
                    .thenReturn(Optional.empty());

            var exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> pageService.getPageById(mockPageId, mockUserId)
            );
            assertEquals(PageMessage.Id.NOT_FOUND, exception.getMessage());
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD return page data WHEN page ID is exists")
        void getPageId_success() {
            var mockPageId = UUID.randomUUID();
            var mockUserId = UUID.randomUUID();

            var mockPageProfile = new PageProfile();
            when(pageRepository.getPageProfileByIdAndUserId(mockPageId, mockUserId))
                    .thenReturn(Optional.of(mockPageProfile));

            var response = pageService.getPageById(mockPageId, mockUserId);
            assertEquals(mockPageProfile, response.pageProfile());
        }
    }

    @Nested
    @DisplayName(value = "Test for: getAllPages")
    class GetAllPageTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return page data list WHEN called")
        void getAllPage_success() {
            var mockUserId = UUID.randomUUID();

            var mockPageProfile = new PageProfile();
            when(pageRepository.getAllPagesByUserId(mockUserId))
                    .thenReturn(List.of(mockPageProfile));

            var response = pageService.getAllPages(mockUserId);
            assertEquals(List.of(mockPageProfile), response.pageProfiles());
        }
    }

    @Nested
    @DisplayName(value = "Test for: getPageByPath")
    class GetPageByPathTest {
        @Test
        @DisplayName(value = "SHOULD throw exception WHEN host path not found")
        void getPageByPath_hostPathNotFound() {
            var mockRequest = new GetPageByPathRequest();

            when(userModuleService.getHostIdByHostPath(mockRequest.host()))
                    .thenReturn(Optional.empty());

            var exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> pageService.getPageByPath(mockRequest)
            );
            assertEquals(UserMessage.DisplayPath.NOT_FOUND, exception.getMessage());

            verify(pageRepository, never()).getPageProfileByDisplayPathAndUserId(any(), any());
        }

        @Test
        @DisplayName(value = "SHOULD throw exception WHEN page path not found")
        void getPageByPath_pagePathNotFound() {
            var mockRequest = new GetPageByPathRequest();

            var mockHostId = UUID.randomUUID();
            when(userModuleService.getHostIdByHostPath(mockRequest.host()))
                    .thenReturn(Optional.of(mockHostId));

            when(pageRepository.getPageProfileByDisplayPathAndUserId(mockRequest.page(), mockHostId))
                    .thenReturn(Optional.empty());

            var exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> pageService.getPageByPath(mockRequest)
            );
            assertEquals(PageMessage.DisplayPath.NOT_FOUND, exception.getMessage());
        }

        @Test
        @DisplayName(value = "SHOULD return data WHEN request is valid")
        void getPageByPath_success() {
            var mockRequest = new GetPageByPathRequest();

            var mockHostId = UUID.randomUUID();
            when(userModuleService.getHostIdByHostPath(mockRequest.host()))
                    .thenReturn(Optional.of(mockHostId));

            var mockPageProfile = new PageProfile();
            when(pageRepository.getPageProfileByDisplayPathAndUserId(mockRequest.page(), mockHostId))
                    .thenReturn(Optional.of(mockPageProfile));

            var response = pageService.getPageByPath(mockRequest);
            assertEquals(mockPageProfile, response.pageProfile());
        }
    }
}