package com.zeus.configuration

import org.eclipse.lmos.arc.agents.Agent
import org.eclipse.lmos.arc.agents.AgentProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AgentRoutingConfiguration {

    @Autowired
    private lateinit var agents: List<Agent<*, *>>

    @Bean
    @Primary
    fun clientAgentProvider(
    ): AgentProvider {
        return AgentProvider {
            agents.sortedBy { if (it.name.lowercase().startsWith("orchestrator")) 0 else 1 }
        }
    }




}