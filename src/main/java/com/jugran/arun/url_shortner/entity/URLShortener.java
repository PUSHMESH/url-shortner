package com.jugran.arun.url_shortner.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.PartitionKey;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "URL_SHORTENER", indexes = {
    @Index(name = "idx_short_url", columnList = "short_url"),
    @Index(name = "idx_user_id", columnList = "user_id")
})
@Builder
public class URLShortener {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "short_url", nullable = false, unique = true, length = 20)
    private String shortUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "click_count", nullable = false)
    @Builder.Default
    private Long clickCount = 0L;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @Column(name = "custom_alias")
    private String customAlias;
}