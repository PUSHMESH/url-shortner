package com.jugran.arun.url_shortner.repository;

import com.jugran.arun.url_shortner.entity.URLShortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLShortenerRepository extends JpaRepository<URLShortener, Long> {

    Optional<URLShortener> findByShortUrl(String shortUrl);

    boolean existsByShortUrl(String shortUrl);
}

