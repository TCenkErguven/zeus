package com.zeus.client;

import com.zeus.data.ScrapeRequestDto;
import com.zeus.data.ScrapeXResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "scraperz", url = "http://localhost:3000/scraper")
public interface ScraperZClient {
    @PostMapping("/x")
    ScrapeXResponseDto scrapeX(@RequestBody ScrapeRequestDto request);
}
