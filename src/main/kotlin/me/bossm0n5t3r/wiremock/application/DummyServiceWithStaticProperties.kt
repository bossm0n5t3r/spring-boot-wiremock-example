package me.bossm0n5t3r.wiremock.application

import com.fasterxml.jackson.databind.ObjectMapper
import me.bossm0n5t3r.wiremock.configuration.readValueWithTypeReference
import me.bossm0n5t3r.wiremock.dto.Product
import me.bossm0n5t3r.wiremock.properties.FakeStoreStaticProperties
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DummyServiceWithStaticProperties(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
) {
    fun getAllProducts(): List<Product> {
        return restTemplate.getForEntity(
            "${FakeStoreStaticProperties.api}/products",
            String::class.java
        ).body
            ?.let {
                objectMapper.readValueWithTypeReference<List<Product>>(it)
            }
            ?: emptyList()
    }
}
