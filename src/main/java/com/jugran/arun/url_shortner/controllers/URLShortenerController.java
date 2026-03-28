package com.jugran.arun.url_shortner.controllers;

import com.jugran.arun.url_shortner.dtos.UrlCreationRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/url")
public class UrlShortnerController {

    @PostMapping(path = "/create", consumes = "application/json")
    void createUrl(@RequestBody UrlCreationRequestDto request){

    }

    @GetMapping(path = "/{shortUrl}")
    void redirect(@PathVariable String shortUrl){

    }
}
