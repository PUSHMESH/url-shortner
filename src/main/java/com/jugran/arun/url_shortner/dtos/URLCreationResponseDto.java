package com.jugran.arun.url_shortner.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class URLCreationResponseDto {
    private boolean success;
    private String message;
    private String originalUrl;
    private String customAlias;
    private String shortUrl;
    private Long urlId;
    private String expirationDate;
}

