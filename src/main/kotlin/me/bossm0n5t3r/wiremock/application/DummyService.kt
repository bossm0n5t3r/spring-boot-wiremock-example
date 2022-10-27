package me.bossm0n5t3r.wiremock.application

import com.fasterxml.jackson.databind.ObjectMapper
import me.bossm0n5t3r.wiremock.configuration.readValueWithTypeReference
import me.bossm0n5t3r.wiremock.dto.Product
import me.bossm0n5t3r.wiremock.properties.FakeStoreProperties
import me.bossm0n5t3r.wiremock.properties.FakeStoreStaticProperties
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DummyService(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    private val fakeStoreProperties: FakeStoreProperties,
) {
    init {
        printProperties("init")
    }

    private final fun printProperties(methodName: String) {
        val header = "=== $methodName ==="
        println(header)
        println("fakeStoreProperties.api: ${fakeStoreProperties.api}")
        println("FakeStoreStaticProperties.api: ${FakeStoreStaticProperties.api}")
        println("=".repeat(header.length))
    }

    fun getAllProducts(): List<Product> {
        printProperties("getAllProducts")
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
