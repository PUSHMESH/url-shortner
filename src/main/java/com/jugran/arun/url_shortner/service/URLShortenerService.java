package com.jugran.arun.url_shortner.service;

import com.jugran.arun.url_shortner.entity.URLShortener;
import com.jugran.arun.url_shortner.entity.User;
import com.jugran.arun.url_shortner.repository.URLShortenerRepository;
import com.jugran.arun.url_shortner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class URLShortenerService implements IURLShortenerService {

    private final URLShortenerRepository urlRepository;
    private final UserRepository userRepository;
    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    /**
     * Create a short URL
     * @param apiDevKey API key for authentication
     * @param originalUrl The original long URL
     * @param customKey Custom short key (optional)
     * @param userName Username of the creator
     * @param expirationDate Expiration date of the short URL
     */
    @Override
    public void createShortUrl(String apiDevKey, String originalUrl, String customKey,
                              String userName, String expirationDate) {
        log.info("Creating short URL for user: {}", userName);

        // Validate user exists
        Optional<User> user = userRepository.findByApiDevKey(apiDevKey);
        if (user.isEmpty()) {
            log.warn("Invalid API dev key: {}", apiDevKey);
            throw new IllegalArgumentException("Invalid API development key");
        }

        // Generate or use custom short key
        String shortKey = (customKey != null && !customKey.isEmpty()) ? 
                          customKey : generateShortKey();

        // Parse expiration date
        LocalDateTime expireDate = null;
        if (expirationDate != null && !expirationDate.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            expireDate = LocalDateTime.parse(expirationDate, formatter);
        }

        // Create and save URL
        URLShortener urlShortener = URLShortener.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortKey)
                .user(user.get())
                .expirationDate(expireDate)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        urlRepository.save(urlShortener);
        log.info("Short URL created: {} -> {}", shortKey, originalUrl);
    }

    /**
     * Redirect to the original URL
     * @param shortUrl The short URL key
     */
    @Override
    public void redirect(String shortUrl) {
        log.info("Redirecting from short URL: {}", shortUrl);

        Optional<URLShortener> urlShortener = urlRepository.findByShortUrl(shortUrl);
        if (urlShortener.isEmpty()) {
            log.warn("Short URL not found: {}", shortUrl);
            throw new IllegalArgumentException("Short URL not found");
        }

        URLShortener url = urlShortener.get();

        // Check if URL is expired
        if (url.getExpirationDate() != null && LocalDateTime.now().isAfter(url.getExpirationDate())) {
            log.warn("Short URL has expired: {}", shortUrl);
            throw new IllegalStateException("Short URL has expired");
        }

        // Check if URL is active
        if (!url.isActive()) {
            log.warn("Short URL is not active: {}", shortUrl);
            throw new IllegalStateException("Short URL is not active");
        }

        // Increment click count
        url.setClickCount(url.getClickCount() + 1);
        url.setLastAccessedAt(LocalDateTime.now());
        urlRepository.save(url);

        log.info("Redirected to: {}", url.getOriginalUrl());
    }

    /**
     * Delete a short URL
     * @param apiDevKey API key for authentication
     * @param urlId URL ID to delete
     */
    @Override
    public void deleteShortUrl(String apiDevKey, int urlId) {
        log.info("Deleting short URL with ID: {}", urlId);

        // Validate user
        Optional<User> user = userRepository.findByApiDevKey(apiDevKey);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid API development key");
        }

        // Find and delete URL
        Optional<URLShortener> urlShortener = urlRepository.findById((long) urlId);
        if (urlShortener.isEmpty()) {
            throw new IllegalArgumentException("URL not found");
        }

        urlRepository.deleteById((long) urlId);
        log.info("Short URL deleted with ID: {}", urlId);
    }

    /**
     * Generate a random short key
     */
    private String generateShortKey() {
        Random random = new Random();
        StringBuilder shortKey = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            shortKey.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        return shortKey.toString();
    }
}
