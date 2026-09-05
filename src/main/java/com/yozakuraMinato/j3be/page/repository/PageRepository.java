package com.yozakuraMinato.j3be.page.repository;

import com.yozakuraMinato.j3be.page.model.Page;
import com.yozakuraMinato.j3be.page.model.type.PageAccess;
import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {
    boolean existsByDisplayPath(String displayPath);

    Optional<PageProfile> getPageProfileByIdAndUserId(UUID id, UUID userId);

    Optional<PageProfile> getPageProfileByDisplayPathAndAccessAndUserId(
            String displayPath, PageAccess access, UUID userId
    );

    Optional<Page> getPageByIdAndUserId(UUID id, UUID userId);

    List<PageProfile> getAllPagesByUserId(UUID userId);

    List<PageProfile> getAllPageProfilesByAccessAndUserId(PageAccess access, UUID userId);

    @Modifying
    @Query(value = """
            update Page page
            set page.isDeleted = true, page.updatedAt = :now, page.updatedBy = :modifierId
            where page.id = :pageId and page.userId = :authorId
            """)
    int softDeletePage(UUID pageId, UUID authorId, Instant now, UUID modifierId);
}
