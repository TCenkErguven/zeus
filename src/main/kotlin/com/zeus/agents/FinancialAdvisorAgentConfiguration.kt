package com.zeus.agents

import org.eclipse.lmos.arc.spring.Agents
import org.eclipse.lmos.arc.agents.dsl.string
import org.eclipse.lmos.arc.agents.dsl.types
import org.eclipse.lmos.arc.spring.Functions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FinancialAdvisorAgentConfiguration {

    @Bean
    fun adviseFinancialFunction(functions: Functions) = functions(
        name = "adviseFinancial",
        params = types(
            string(
                name = "input",
                description = "The input data for financial advice, which can include stock trends, financial data, and posts from x (formerly Twitter)."
            )
        ),
        description = """
            This function provides financial advice based on the input data.
            It analyzes stock trends, financial data, and relevant posts from x (formerly Twitter).
        """.trimIndent()
    ) { (input) ->
        // Here you would implement the logic to analyze the input and provide financial advice.
        // For now, we return a placeholder response.
        "Financial advice based on input: $input"
    }

    @Bean
    fun financialAdvisorAgent(agent: Agents) = agent {
        name = "financialAdvisorAgent"
        model = { "GPT-4o" }
        description = """
            This agent is responsible for providing financial advice based on stock trends, financial data, and posts from x (formerly Twitter).
            """
        systemPrompt = {
            """
            You are a financial advisor agent.
            Your input will be base on x formerly known as Twitter and nasdaq.
            You will give financial advice based on the information provided by the input.
            Those information will be related about stocks, trends, financial data and x formerly known as Twitter posts.
            At the end you will give the best financial advice you can give based on the input data.
            This agent will give technical analysis, fundamental analysis and sentiment analysis based on the input data.
            This agent will give the technical data about the stocks, trends and financial data.
                            """.trimIndent()
        }
        tools = listOf("adviseFinancial")
    }



}