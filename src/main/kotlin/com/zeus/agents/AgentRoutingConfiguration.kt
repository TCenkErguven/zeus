package com.zeus.agents

import org.eclipse.lmos.arc.agents.Agent
import org.eclipse.lmos.arc.agents.AgentProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AgentRoutingConfiguration {

    @Bean
    @Primary
    fun clientAgentProvider(
        orchestratorAgent: Agent<*, *>,
        xAgent: Agent<*, *>,
        nasdaqAgent: Agent<*, *>,
        financialAdvisorAgent: Agent<*, *>,
    ): AgentProvider {
        return AgentProvider {
            listOf(orchestratorAgent, xAgent, nasdaqAgent, financialAdvisorAgent)
        }
    }




}