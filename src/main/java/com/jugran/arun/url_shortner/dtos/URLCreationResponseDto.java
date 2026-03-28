package com.jugran.arun.url_shortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for URL Creation Response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class URLCreationResponseDto {
    private boolean success;
    private String message;
    private String originalUrl;
    private String customAlias;
    private String shortUrl;
    private Long urlId;
    private String expirationDate;
}

