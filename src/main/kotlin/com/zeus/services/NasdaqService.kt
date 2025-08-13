package com.zeus.services

import com.zeus.client.NasdaqClient
import com.zeus.data.NasdaqSymbolResponseDto
import org.springframework.stereotype.Service

@Service
class NasdaqService(
    private val nasdaqClient: NasdaqClient
) {

    fun fetchNasdaqSymbols(): List<NasdaqSymbolResponseDto> {
        return nasdaqClient.fetchNasdaqSymbols()
    }

}