package com.zeus.agents

import com.zeus.services.ScrapeService
import org.eclipse.lmos.arc.agents.dsl.string
import org.eclipse.lmos.arc.agents.dsl.types
import org.eclipse.lmos.arc.spring.Agents
import org.eclipse.lmos.arc.spring.Functions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class XAgentConfiguration(
    private val scrapeService: ScrapeService
) {

    @Bean
    fun scrapeXFunction(functions: Functions) = functions(
        name = "scrapeX",
        params = types(
            string(
                name = "hashtag",
                description = "The hashtag to search for on X (formerly Twitter)"
            )
        ),
        description = """
            This function scrapes content from X (formerly Twitter) based on the provided hashtag.
            It returns tweets related to the specified hashtag.
        """.trimIndent()
    ) { (hashtag) ->
        scrapeService.scrapeX(hashtag.toString())
    }

    @Bean
    fun xAgent(agent: Agents) = agent {
        name = "xAgent"
        model = { "GPT-4o" }
        description = """
            This agent is responsible for scraping content from X (formerly Twitter).
        """.trimIndent()
        systemPrompt = {
            """
            You are an agent responsible for scraping content from X (formerly Twitter).
            You can search for tweets based on hashtags and provide relevant content to users.
            This agent is only called by the orchestrator agent and handles all X platform operations.
            This agent cannot be called by client directly, it can only be called by orchestrator agent.
            """.trimIndent()
        }
        tools = listOf("scrapeX")
    }
}