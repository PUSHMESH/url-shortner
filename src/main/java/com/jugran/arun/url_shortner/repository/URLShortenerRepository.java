package com.jugran.arun.url_shortner.repository;

import com.jugran.arun.url_shortner.entity.URLShortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for URLShortener entity
 */
@Repository
public interface URLShortenerRepository extends JpaRepository<URLShortener, Long> {
    
    /**
     * Find a URL shortener by its short URL key
     */
    Optional<URLShortener> findByShortUrl(String shortUrl);
    
    /**
     * Check if a short URL already exists
     */
    boolean existsByShortUrl(String shortUrl);
}

