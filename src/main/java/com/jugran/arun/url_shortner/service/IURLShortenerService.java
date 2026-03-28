package com.jugran.arun.url_shortner.service;

public interface IUrlShortnerService {
    void createShortUrl(String apiDevKey, String originalUrl, String customKey,
                        String userName, String expirationDate);
    void redirect(String shortUrl);
    void deleteShortUrl(String apiDevKey, int urlId);
}
