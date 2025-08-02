package com.zeus.configuration

import com.zeus.services.XService
import org.eclipse.lmos.arc.agents.dsl.string
import org.eclipse.lmos.arc.agents.dsl.types
import org.eclipse.lmos.arc.spring.Agents
import org.eclipse.lmos.arc.spring.Functions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class XAgentConfiguration {
    private val xService: XService

    constructor(xService: XService) {
        this.xService = xService
    }

    @Bean
    fun scrapeXFunction(functions: Functions) = functions(
        name = "scrapeX",
        params =
            types(
                string(
                    name = "hashtag",
                    description = "The hashtag to scrape content from x (formerly Twitter)."
                )
            ),
        description = """
            Scrapes content from the x formerly known as Twitter. Based on given hashtag"
        """.trimIndent()
    ){
        (hashtag) ->
            xService.scrapeX(hashtag = hashtag.toString())
    }

    @Bean
    fun xAgent(agent: Agents) = agent {
        name = "xAgent"
        model = { "GPT-4o" }
        description = "You are an agent that scrapes content from x (formerly Twitter) based on a given hashtag."
        tools = listOf("scrapeX")
    }
}