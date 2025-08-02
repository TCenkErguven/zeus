package com.zeus.data

data class ScrapeXResponseDto(
    val tweets: List<List<String>>,
    val type: String
)