package com.jugran.arun.url_shortner.service;

public interface IUrlShortnerService {
    void createShortUrl(String originalUrl, String customKey,
                        String userName, String expirationDate);
    void redirect(String url);
    void deleteShortUrl(int urlId);
}
