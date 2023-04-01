package me.bossm0n5t3r.wiremock.application

import com.fasterxml.jackson.databind.ObjectMapper
import me.bossm0n5t3r.wiremock.configuration.readValueWithTypeReference
import me.bossm0n5t3r.wiremock.dto.Product
import me.bossm0n5t3r.wiremock.properties.FakeStoreProperties
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DummyServiceWithNonStaticProperties(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    private val fakeStoreProperties: FakeStoreProperties,
) {
    fun getAllProductsUsingRestTemplate(): List<Product> {
        return try {
            restTemplate.getForEntity(
                "${fakeStoreProperties.api}/products",
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
