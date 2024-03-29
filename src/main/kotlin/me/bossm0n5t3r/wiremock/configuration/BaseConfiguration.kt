package me.bossm0n5t3r.wiremock.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class BaseConfiguration {
    @Bean
    fun objectMapper() = jacksonObjectMapper()

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun webClient() = WebClient.create()
}
