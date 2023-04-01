package me.bossm0n5t3r.wiremock.application

import com.fasterxml.jackson.databind.ObjectMapper
import me.bossm0n5t3r.wiremock.configuration.readValueWithTypeReference
import me.bossm0n5t3r.wiremock.dto.Product
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DummyRestTemplateSupporter(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
) {
    fun getAllProducts(url: String): List<Product> {
        return try {
            restTemplate.getForEntity(
                url,
                String::class.java
            ).body
                ?.let {
                    objectMapper.readValueWithTypeReference<List<Product>>(it)
                }
                ?: emptyList()
        } catch (e: Exception) {
            error(e)
        }
    }
}
