package com.zeus.services

import com.zeus.client.ScraperClient
import com.zeus.data.NasdaqSymbolResponseDto
import com.zeus.data.ScrapeNasdaqResponseDto
import com.zeus.data.ScrapeRequestDto
import com.zeus.data.ScrapeXResponseDto
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ScrapeService(
    private val scraperClient: ScraperClient
) {

    //OVERRIDE TO STRING TO AVOID REFERENCES

    @Cacheable(value = ["scrapeXCache"], key = "#hashtag")
    fun scrapeX(hashtag: String): String {
        val payload = ScrapeRequestDto(value = hashtag)
        val response: ScrapeXResponseDto = scraperClient.scrapeX(payload)
        return response.tweets.toString()
    }

    @Cacheable(value = ["scrapeNasdaq"], key = "#symbol")
    fun scrapeNasdaq(symbol: String): String{
        val payload = ScrapeRequestDto(value = symbol);
        return scraperClient.scrapeNasdaq(payload).toString();
    }


}