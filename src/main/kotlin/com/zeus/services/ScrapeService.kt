package com.zeus.services

import com.zeus.client.ScraperClient
import com.zeus.data.NasdaqSymbolResponseDto
import com.zeus.data.ScrapeNasdaqResponseDto
import com.zeus.data.ScrapeRequestDto
import com.zeus.data.ScrapeXResponseDto
import org.springframework.stereotype.Service

@Service
class ScrapeService(
    private val scraperClient: ScraperClient
) {

    fun scrapeX(hashtag: String): String {
        val payload = ScrapeRequestDto(value = hashtag)
        val response: ScrapeXResponseDto = scraperClient.scrapeX(payload)
        return response.tweets.toString()
    }

    fun scrapeNasdaq(symbol: String): ScrapeNasdaqResponseDto{
        val payload = ScrapeRequestDto(value = symbol);
        return scraperClient.scrapeNasdaq(payload);
    }


}