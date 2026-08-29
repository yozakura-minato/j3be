package com.yozakuraMinato.j3be.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
public class AuditEntityBase {
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    @Column(name = "updated_at", insertable = false)
    private Instant updatedAt;

    @Column(name = "updated_by", insertable = false)
    private UUID updatedBy;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}