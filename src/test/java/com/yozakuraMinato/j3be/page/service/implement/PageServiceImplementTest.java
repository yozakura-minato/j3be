package com.yozakuraMinato.j3be.page.service.implement;

import com.yozakuraMinato.j3be.common.exception.custom.ResourceAccessDeniedException;
import com.yozakuraMinato.j3be.common.exception.custom.ResourceConflictException;
import com.yozakuraMinato.j3be.common.exception.custom.ResourceNotFoundException;
import com.yozakuraMinato.j3be.page.model.Page;
import com.yozakuraMinato.j3be.page.model.type.PageAccess;
import com.yozakuraMinato.j3be.page.repository.PageRepository;
import com.yozakuraMinato.j3be.page.util.PageFactory;
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

import java.time.Instant;
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
            // Arrange
            var mockRequest = PageFactory.createPageRequest(PageAccess.PUBLIC);
            var mockUserId = UUID.randomUUID();

            when(pageRepository.existsByDisplayPath(mockRequest.displayPath()))
                    .thenReturn(true);

            // Act
            var exception = assertThrows(
                    ResourceConflictException.class,
                    () -> pageService.createPage(mockRequest, mockUserId)
            );

            // Assert
            verify(pageRepository, times(1)).existsByDisplayPath(mockRequest.displayPath());

            assertEquals(PageMessage.DisplayPath.CONFLICT, exception.getMessage());

            verify(pageRepository, never()).save(any(Page.class));
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD map and save WHEN request is valid")
        void createPage_Success() {
            // Arrange
            var mockRequest = PageFactory.createPageRequest(PageAccess.PUBLIC);
            var mockUserId = UUID.randomUUID();

            when(pageRepository.existsByDisplayPath(mockRequest.displayPath()))
                    .thenReturn(false);

            var mockEntity = new Page();
            when(pageMapper.createPageRequestDtoToEntity(mockRequest))
                    .thenReturn(mockEntity);

            var beforeCreate = Instant.now();

            // Act
            pageService.createPage(mockRequest, mockUserId);

            // Assert
            verify(pageRepository, times(1)).save(pageCaptor.capture());

            var savedPage = pageCaptor.getValue();
            assertEquals(mockUserId, savedPage.getUserId());
            assertNotNull(savedPage.getCreatedAt());
            assertTrue(
                    beforeCreate.isBefore(savedPage.getCreatedAt())
                            || beforeCreate.equals(savedPage.getCreatedAt())
            );
            assertEquals(mockUserId, savedPage.getCreatedBy());
        }
    }

    @Nested
    @DisplayName(value = "Test for: getPageById")
    class GetOfPageByIdTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD throw exception WHEN page ID not found")
        void getPageById_PageIdNotFound() {
            // Arrange
            var mockPageId = UUID.randomUUID();
            var mockUserId = UUID.randomUUID();

            when(pageRepository.getPageProfileByIdAndUserId(mockPageId, mockUserId))
                    .thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> pageService.getPageById(mockPageId, mockUserId)
            );

            // Assert
            verify(pageRepository, times(1)).getPageProfileByIdAndUserId(mockPageId, mockUserId);

            assertEquals(PageMessage.Id.NOT_FOUND, exception.getMessage());
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD return page data WHEN page ID is exists")
        void getPageId_success() {
            // Arrange
            var mockPageId = UUID.randomUUID();
            var mockUserId = UUID.randomUUID();

            var mockPageProfile = PageFactory.pageProfile(PageAccess.PUBLIC);
            when(pageRepository.getPageProfileByIdAndUserId(mockPageId, mockUserId))
                    .thenReturn(Optional.of(mockPageProfile));

            // Act
            var response = pageService.getPageById(mockPageId, mockUserId);

            // Assert
            verify(pageRepository, times(1)).getPageProfileByIdAndUserId(mockPageId, mockUserId);

            assertEquals(mockPageProfile, response.pageProfile());
        }
    }

    @Nested
    @DisplayName(value = "Test for: getAllPages")
    class GetAllPageTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return page data list WHEN called")
        void getAllPage_success() {
            // Arrange
            var mockUserId = UUID.randomUUID();

            var mockPageProfile = PageFactory.pageProfile(PageAccess.PUBLIC);
            when(pageRepository.getAllPagesByUserId(mockUserId))
                    .thenReturn(List.of(mockPageProfile));

            // Act
            var response = pageService.getAllPages(mockUserId);

            // Assert
            verify(pageRepository, times(1)).getAllPagesByUserId(mockUserId);

            assertEquals(List.of(mockPageProfile), response.pageProfiles());
        }
    }

    @Nested
    @DisplayName(value = "Test for: getPageByPath")
    class GetPageByPathTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD throw exception WHEN host path not found")
        void getPageByPath_hostPathNotFound() {
            // Arrange
            var mockRequest = PageFactory.getPageByPathRequest();

            when(userModuleService.getHostIdByHostPath(mockRequest.host()))
                    .thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> pageService.getPageByPath(mockRequest)
            );

            //Assert
            verify(userModuleService, times(1)).getHostIdByHostPath(mockRequest.host());

            assertEquals(UserMessage.DisplayPath.NOT_FOUND, exception.getMessage());

            verify(pageRepository, never()).getPageProfileByDisplayPathAndUserId(any(String.class), any(UUID.class));
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN page path not found")
        void getPageByPath_pagePathNotFound() {
            // Arrange
            var mockRequest = PageFactory.getPageByPathRequest();

            var mockHostId = UUID.randomUUID();
            when(userModuleService.getHostIdByHostPath(mockRequest.host()))
                    .thenReturn(Optional.of(mockHostId));

            when(pageRepository.getPageProfileByDisplayPathAndUserId(mockRequest.page(), mockHostId))
                    .thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> pageService.getPageByPath(mockRequest)
            );

            // Assert
            verify(userModuleService, times(1)).getHostIdByHostPath(mockRequest.host());
            verify(pageRepository, times(1)).getPageProfileByDisplayPathAndUserId(
                    mockRequest.page(), mockHostId
            );

            assertEquals(PageMessage.DisplayPath.NOT_FOUND, exception.getMessage());
        }

        @Test
        @DisplayName(value = "Scenario 3: SHOULD throw exception WHEN page access is not Public")
        void getPageByPath_accessIsNotPublic() {
            // Arrange
            var mockRequest = PageFactory.getPageByPathRequest();

            var mockHostId = UUID.randomUUID();
            when(userModuleService.getHostIdByHostPath(mockRequest.host()))
                    .thenReturn(Optional.of(mockHostId));

            var mockPageProfile = PageFactory.pageProfile(PageAccess.PRIVATE);
            when(pageRepository.getPageProfileByDisplayPathAndUserId(mockRequest.page(), mockHostId))
                    .thenReturn(Optional.of(mockPageProfile));

            // Act
            var exception = assertThrows(
                    ResourceAccessDeniedException.class,
                    () -> pageService.getPageByPath(mockRequest)
            );

            // Assert
            verify(userModuleService, times(1)).getHostIdByHostPath(mockRequest.host());
            verify(pageRepository, times(1)).getPageProfileByDisplayPathAndUserId(
                    mockRequest.page(), mockHostId
            );

            assertEquals(PageMessage.Access.DENIED, exception.getMessage());
        }

        @Test
        @DisplayName(value = "Scenario 4: SHOULD return data WHEN request is valid")
        void getPageByPath_success() {
            // Arrange
            var mockRequest = PageFactory.getPageByPathRequest();

            var mockHostId = UUID.randomUUID();
            when(userModuleService.getHostIdByHostPath(mockRequest.host()))
                    .thenReturn(Optional.of(mockHostId));

            var mockPageProfile = PageFactory.pageProfile(PageAccess.PUBLIC);
            when(pageRepository.getPageProfileByDisplayPathAndUserId(mockRequest.page(), mockHostId))
                    .thenReturn(Optional.of(mockPageProfile));

            // Act
            var response = pageService.getPageByPath(mockRequest);

            // Assert
            verify(userModuleService, times(1)).getHostIdByHostPath(mockRequest.host());
            verify(pageRepository, times(1)).getPageProfileByDisplayPathAndUserId(
                    mockRequest.page(), mockHostId
            );

            assertEquals(mockPageProfile, response.pageProfile());
        }
    }

    @Nested
    @DisplayName(value = "Test for: updatePage")
    class UpdatePageTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD throw exception WHEN page ID not found")
        void updatePage_pageIdNotFound() {
            // Arrange
            var mockPageId = UUID.randomUUID();
            var mockRequest = PageFactory.updatePageRequest(PageAccess.PUBLIC);
            var mockUserId = UUID.randomUUID();

            when(pageRepository.getPageByIdAndUserId(mockPageId, mockUserId))
                    .thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> pageService.updatePage(mockPageId, mockRequest, mockUserId)
            );

            // Assert
            verify(pageRepository, times(1)).getPageByIdAndUserId(mockPageId, mockUserId);

            assertEquals(PageMessage.Id.NOT_FOUND, exception.getMessage());
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD update WHEN request is valid")
        void updatePage_success() {
            // Arrange
            var mockPageId = UUID.randomUUID();
            var mockRequest = PageFactory.updatePageRequest(PageAccess.PUBLIC);
            var mockUserId = UUID.randomUUID();

            var mockExistsPage = new Page();
            when(pageRepository.getPageByIdAndUserId(mockPageId, mockUserId))
                    .thenReturn(Optional.of(mockExistsPage));

            var beforeUpdate = Instant.now();

            // Act
            pageService.updatePage(mockPageId, mockRequest, mockUserId);

            // Assert
            verify(pageRepository, times(1)).getPageByIdAndUserId(mockPageId, mockUserId);

            assertNotNull(mockExistsPage.getUpdatedAt());
            assertTrue(
                    beforeUpdate.isBefore(mockExistsPage.getUpdatedAt())
                            || beforeUpdate.equals(mockExistsPage.getUpdatedAt())
            );
            assertEquals(mockUserId, mockExistsPage.getUpdatedBy());
        }
    }

    @Nested
    @DisplayName(value = "Test for: softDeletePage")
    class SoftDeletePageTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD throw exception WHEN page ID not found")
        void softDeletePage_pageIdNotFound() {
            // Arrange
            var mockPageId = UUID.randomUUID();
            var mockUserId = UUID.randomUUID();

            when(pageRepository.softDeletePage(eq(mockPageId), eq(mockUserId), any(Instant.class), eq(mockUserId)))
                    .thenReturn(0);

            // Act
            var exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> pageService.softDeletePage(mockPageId, mockUserId)
            );

            // Assert
            verify(pageRepository, times(1)).softDeletePage(
                    eq(mockPageId), eq(mockUserId), any(Instant.class), eq(mockUserId)
            );

            assertEquals(PageMessage.Id.NOT_FOUND, exception.getMessage());
        }

        @Test
        @DisplayName(value = "SHOULD success WHEN iDs is valid")
        void softDeletePage_success() {
            // Arrange
            var mockPageId = UUID.randomUUID();
            var mockUserId = UUID.randomUUID();

            when(pageRepository.softDeletePage(eq(mockPageId), eq(mockUserId), any(Instant.class), eq(mockUserId)))
                    .thenReturn(1);

            // Act
            pageService.softDeletePage(mockPageId, mockUserId);

            // Assert
            verify(pageRepository, times(1)).softDeletePage(
                    eq(mockPageId), eq(mockUserId), any(Instant.class), eq(mockUserId)
            );
        }
    }
}
