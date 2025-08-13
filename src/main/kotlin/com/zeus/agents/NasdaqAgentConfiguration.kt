package com.zeus.agents

import com.zeus.services.NasdaqService
import com.zeus.services.ScrapeService
import org.eclipse.lmos.arc.agents.dsl.string
import org.eclipse.lmos.arc.agents.dsl.types
import org.eclipse.lmos.arc.spring.Agents
import org.eclipse.lmos.arc.spring.Functions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NasdaqAgentConfiguration(
    private val nasdaqService: NasdaqService,
    private val scrapeService: ScrapeService
) {

    @Bean
    fun listNasdaqSymbolsFunction(functions: Functions) = functions(
        name = "fetchNasdaqSymbols",
        params = types(string(name = "dummy", description = "Dummy parameter for fetching NASDAQ symbols")),
        description = """
            This function fetches the list of NASDAQ stock symbols and their names."
            This function only returns the list of NASDAQ stock symbols and their names.
            This function will show the stocks on market if user wanted to learn which stocks are available on NASDAQ.
        """.trimIndent()
    ){
            (dummy) ->
        nasdaqService.fetchNasdaqSymbols().toString().take(3000);
    }

    @Bean
    fun scrapeNasdaqFunction(functions: Functions) = functions(
        name = "scrapeNasdaq",
        params =
            types(
                string(
                    name = "symbol",
                    description = "The symbol of the NASDAQ stock to scrape information for."
                )
            ),
        description = """
            This function scrapes NASDAQ stock information based on the provided symbol."
            It returns detailed information about the stock, including its current price, market cap, and other relevant data.
        """.trimIndent()
    ){
            (symbol) ->
        scrapeService.scrapeNasdaq(symbol.toString()).toString();
    }

    @Bean
    fun nasdaqAgent(agent: Agents) = agent {
        name = "nasdaqAgent"
        model = { "GPT-4o" }
        description = """
            This agent is responsible for providing information about NASDAQ stocks.
        """.trimIndent()
        systemPrompt = {
            """
            You are an agent responsible only about NASDAQ stock apis supplied to you.
            You can list the stock information from NASDAQ exchange to user.
            You can also provide information about NASDAQ stock symbols and their details.
            You can analyze trends, prices, and market data from NASDAQ API and suggest the best analyze you can give.
            This function only called by orchestrator agent and only responsible for NASDAQ implementations.
            This agent only called by orchestrator agent, it can not call itself.
            This agent cannot be called by client directly, it can only be called by orchestrator agent.
            """.trimIndent()
        }
        tools = listOf("fetchNasdaqSymbols","scrapeNasdaq")
    }
}