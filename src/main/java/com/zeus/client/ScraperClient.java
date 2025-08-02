package com.zeus.client;

import com.zeus.data.ScrapeXRequestDto;
import com.zeus.data.ScrapeXResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "scraper", url = "http://localhost:3000/scraper")
public interface ScraperClient {
    @PostMapping("/x")
    ScrapeXResponseDto scrapeX(@RequestBody ScrapeXRequestDto request);
}
