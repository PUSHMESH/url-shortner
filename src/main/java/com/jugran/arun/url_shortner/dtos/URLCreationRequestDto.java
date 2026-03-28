package com.jugran.arun.url_shortner.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class URLCreationRequestDto {
    private String apiDevKey;
    private String originalUrl;
    private String customAlias;
    private String userName;
    private String expireDate;
}
