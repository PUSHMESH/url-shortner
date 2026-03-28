package com.jugran.arun.url_shortner.controllers;

import com.jugran.arun.url_shortner.dtos.URLCreationRequestDto;
import com.jugran.arun.url_shortner.dtos.URLCreationResponseDto;
import com.jugran.arun.url_shortner.service.IURLShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/url")
@Slf4j
@Tag(name = "URL Shortener", description = "APIs for creating, retrieving, and deleting shortened URLs")
@RequiredArgsConstructor
public class URLShortenerController {

    private final IURLShortenerService urlShortenerService;

    /**
     * Create a new short URL
     * @param request URL creation request containing original URL and optional custom alias
     * @return ResponseEntity with created short URL details
     */
    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create a shortened URL", description = "Creates a new shortened URL from a long URL")
    public ResponseEntity<URLCreationResponseDto> createUrl(
            @RequestBody URLCreationRequestDto request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        try {
            log.info("Creating short URL for original URL: {}", request.getOriginalUrl());
            
            urlShortenerService.createShortUrl(
                request.getApiDevKey(),
                request.getOriginalUrl(),
                request.getCustomAlias(),
                request.getUserName(),
                request.getExpireDate()
            );
            
            URLCreationResponseDto response = URLCreationResponseDto.builder()
                    .success(true)
                    .message("Short URL created successfully")
                    .originalUrl(request.getOriginalUrl())
                    .customAlias(request.getCustomAlias())
                    .build();
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            log.error("Invalid request: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(URLCreationResponseDto.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .build());
        } catch (Exception ex) {
            log.error("Error creating short URL", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(URLCreationResponseDto.builder()
                            .success(false)
                            .message("Error creating short URL: " + ex.getMessage())
                            .build());
        }
    }

    /**
     * Redirect to the original URL
     * @param shortUrl The short URL key
     * @return ResponseEntity with redirect information or error
     */
    @GetMapping(path = "/{shortUrl}", produces = "application/json")
    @Operation(summary = "Redirect to original URL", description = "Redirects to the original URL for a given short URL key")
    public ResponseEntity<String> redirect(@PathVariable String shortUrl) {
        try {
            log.info("Redirecting from short URL: {}", shortUrl);
            urlShortenerService.redirect(shortUrl);
            return ResponseEntity.status(HttpStatus.FOUND).body("Redirecting to original URL");
        } catch (IllegalArgumentException ex) {
            log.warn("Short URL not found: {}", shortUrl);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalStateException ex) {
            log.warn("URL access error: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.GONE).body(ex.getMessage());
        }
    }

    /**
     * Delete a short URL
     * @param urlId The ID of the URL to delete
     * @param apiDevKey The API dev key for authentication
     * @return ResponseEntity with success/failure message
     */
    @DeleteMapping(path = "/{urlId}", produces = "application/json")
    @Operation(summary = "Delete a shortened URL", description = "Deletes a shortened URL by its ID")
    public ResponseEntity<String> deleteUrl(
            @PathVariable int urlId,
            @RequestParam String apiDevKey) {
        
        try {
            log.info("Deleting short URL with ID: {}", urlId);
            urlShortenerService.deleteShortUrl(apiDevKey, urlId);
            return ResponseEntity.status(HttpStatus.OK).body("Short URL deleted successfully");
        } catch (IllegalArgumentException ex) {
            log.error("Invalid request: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Error deleting short URL", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting short URL: " + ex.getMessage());
        }
    }
}
