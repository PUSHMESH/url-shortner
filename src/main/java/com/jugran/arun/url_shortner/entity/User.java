package com.jugran.arun.url_shortner.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@TableGenerator(
    name = "user_gen",
    table = "SEQUENCE_GENERATOR",
    initialValue = 1000,
    allocationSize = 1
)
@Table(name = "USERS", indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_api_dev_key", columnList = "api_dev_key")
})
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_gen")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "api_dev_key", nullable = false, unique = true)
    private String apiDevKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_group_id")
    private OrgGroup orgGroup;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private boolean isActive = true;
}