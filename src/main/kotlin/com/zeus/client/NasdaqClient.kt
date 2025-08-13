package com.zeus.client

import com.zeus.data.NasdaqSymbolResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "nasdaq", url = "http://localhost:3000/nasdaq")
interface NasdaqClient {
    @GetMapping("/symbols")
    fun fetchNasdaqSymbols(): List<NasdaqSymbolResponseDto>
}