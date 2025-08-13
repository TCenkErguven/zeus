package com.zeus.data

data class ScrapeNasdaqResponseDto(
    val candles: List<Candle>,
    val rsis: List<Rsi>
)

data class Candle(
    val adjustedClose: String,
    val date: String,
    val open: String,
    val high: String,
    val low: String,
    val close: String,
    val volume: String
)

data class Rsi(
    val date: String,
    val value: Long
)
