package com.yozakuraMinato.j3be.page.repository;

import com.yozakuraMinato.j3be.page.model.Page;
import com.yozakuraMinato.j3be.page.repository.projection.PageProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {
    boolean existsByDisplayPath(String displayPath);

    Optional<PageProfile> getPageProfileByIdAndUserId(UUID id, UUID userId);

    Optional<PageProfile> getPageProfileByDisplayPathAndUserId(String displayPath, UUID userId);

    List<PageProfile> getAllPagesByUserId(UUID userId);
}