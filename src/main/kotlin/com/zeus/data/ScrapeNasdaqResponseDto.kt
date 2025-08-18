package com.zeus.data

data class ScrapeNasdaqResponseDto(
    val candles: List<Candle>,
    val rsis: List<Rsi>
)