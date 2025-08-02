package com.zeus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableFeignClients
@ComponentScan("org.eclipse.lmos.arc", "com.zeus.*")
class ArcAIApplication

fun main(args: Array<String>) {
    runApplication<ArcAIApplication>(*args)
}