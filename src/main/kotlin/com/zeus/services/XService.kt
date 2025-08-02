package com.zeus.services

import com.zeus.client.ScraperXClient
import com.zeus.data.ScrapeXRequestDto
import com.zeus.data.ScrapeXResponseDto
import org.springframework.stereotype.Service

@Service
class XService(
    private val scraperClient: ScraperXClient
) {

    fun scrapeX(hashtag: String): String {
        val payload = ScrapeXRequestDto(hashtag = hashtag)
        val response: ScrapeXResponseDto = scraperClient.scrapeX(payload)

        println("XService is doing something: $hashtag")
        return response.tweets.toString()
    }


}