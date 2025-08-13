package com.zeus.agents

import org.eclipse.lmos.arc.agents.conversation.Conversation
import org.eclipse.lmos.arc.agents.dsl.boolean
import org.eclipse.lmos.arc.agents.dsl.extensions.askAgent
import org.eclipse.lmos.arc.agents.dsl.extensions.callAgent
import org.eclipse.lmos.arc.agents.dsl.extensions.memory
import org.eclipse.lmos.arc.agents.dsl.get
import org.eclipse.lmos.arc.agents.dsl.string
import org.eclipse.lmos.arc.agents.dsl.types
import org.eclipse.lmos.arc.spring.Agents
import org.eclipse.lmos.arc.spring.Functions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrchestratorAgentConfiguration {

    fun askCustomAgent(functions: Functions) = functions(
        name = "askCustomAgent",
        params = types(
            string(name = "name", description = "The name of the agent to call."),
            boolean(name = "status", description = "The status of the agent."),
            string(name = "askAgentInput", description = "The input to ask the agent if status is false.")
        ),
        description = """
            This function calls a custom agent based on the provided name.
        """.trimIndent()
    ) { params ->
        val name = params[0] ?: error("Missing parameter 'name'")
        val status = params[1] as? Boolean ?: error("Missing or invalid parameter 'status'")
        val askAgentInput = params[2] ?: error("Missing parameter 'askAgentInput'")
        val agentName = name.toString()
        val currentConversation = get<Conversation>()

        val result = if (status) {
            callAgent(agentName, currentConversation)
        } else {
            askAgent(agentName, input = askAgentInput as String)
        }
        result.toString()
    }

    @Bean
    fun askFinancialAdvice(functions: Functions) = functions(
        name = "askFinancialAdvice",
        params = types(
            string(name = "symbol", description = "The stock symbol to get financial advice for.")
        ),
        description = """
        This function provides financial advice based on the stock symbol and the agents' outputs.
        It uses the xAgent to scrape content from x (formerly Twitter) based on a given hashtag,
        and the nasdaqAgent to provide information about NASDAQ stocks, including listing symbols and scraping stock data.
        The financialAdvisorAgent will then provide financial advice based on the inputs gathered from xAgent and nasdaqAgent output data.
        Agent list provided below:
        - xAgent: Scrapes content from x (formerly Twitter) based on a given hashtag.
        - nasdaqAgent: Provides information about NASDAQ stocks, including listing symbols and scraping stock data.
        - financialAdvisorAgent: Provides financial advice based on stock trends, financial data, and posts from x (formerly Twitter).
        Inputs will be based on the inputs gathered from xAgent and nasdaqAgent output data.
    """.trimIndent()
    ) { (symbol) ->

        //val currentConversation = get<Conversation>()
        memory<String>(symbol.toString())

        //val xInput = callAgent("xAgent", input = currentConversation)
        val xInput = askAgent("xAgent", input = symbol.toString())
        //val nasdaqInput = callAgent("nasdaqAgent", input = currentConversation)
        val nasdaqInput = askAgent("nasdaqAgent", input = symbol.toString())

        val financialAdvisorInput = """
            xInput: $xInput
            nasdaqInput: $nasdaqInput
        """.trimIndent()
        val result = askAgent("financialAdvisorAgent", input = financialAdvisorInput)
        result.toString()
    }

    @Bean
    fun orchestratorAgent(agent: Agents) = agent {
        name = "orchestratorAgent"
        model = { "GPT-4o" }
        description = """
            This agent is responsible for orchestrating the other agents.
        """.trimIndent()
        systemPrompt = {
            """
            You are a supervisor agent.
            You can call other agents to get their help.
            Call the "xAgent" if you need x (formerly Twitter) information.
            Call the "nasdaqAgent" if you need information about NASDAQ about a specific stock or list the stocks.
            This agent is responsible for coordinating the other agents and providing a unified interface for the user.
            This agent can't call itself; it can only call other agents.
            This agent is responsible for the interaction with the user and orchestrating the other agents to provide the best possible response.
            This agent will not give direct information about x or NASDAQ; it will only call the other agents to get the information.
            This agent will get the stock symbol from the user, then ask nasdaqAgent to get the stock information and then ask xAgent to get the related posts from x (formerly Twitter).
            This agent will then call financialAdvisorAgent to get the financial advice based on the information provided by the other agents.
            This agent will then return the final response to the user.
            This agent will only be called by the client and will not be called by other agents.
            This agent will not be called by more than one agent at a time; it will only be called by the client.
            """.trimIndent()
        }
        tools = listOf("askFinancialAdvice")
    }
}