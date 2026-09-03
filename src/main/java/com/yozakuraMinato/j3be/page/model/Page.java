package com.yozakuraMinato.j3be.page.model;

import com.yozakuraMinato.j3be.page.model.type.PageAccess;
import com.yozakuraMinato.j3be.persistence.model.AuditEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "pages")
@SQLRestriction(value = "is_deleted = false")
@Setter
@Getter
public class Page extends AuditEntityBase {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "display_path", nullable = false)
    private String displayPath;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "access", nullable = false)
    @Enumerated(EnumType.STRING)
    private PageAccess access = PageAccess.PRIVATE;
}
