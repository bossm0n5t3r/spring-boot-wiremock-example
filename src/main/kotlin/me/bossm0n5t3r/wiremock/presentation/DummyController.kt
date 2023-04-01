package me.bossm0n5t3r.wiremock.presentation

import me.bossm0n5t3r.wiremock.application.DummyServiceWithNonStaticProperties
import me.bossm0n5t3r.wiremock.application.DummyServiceWithStaticProperties
import me.bossm0n5t3r.wiremock.dto.Product
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DummyController(
    private val dummyServiceWithNonStaticProperties: DummyServiceWithNonStaticProperties,
    private val dummyServiceWithStaticProperties: DummyServiceWithStaticProperties,
) {
    @GetMapping("/non-static/products")
    fun getAllProductsWithNonStaticProperties(): List<Product> {
        return dummyServiceWithNonStaticProperties.getAllProductsUsingRestTemplate()
    }

    @GetMapping("/static/products")
    fun getAllProductsWithStaticProperties(): List<Product> {
        return dummyServiceWithStaticProperties.getAllProductsUsingRestTemplate()
    }
}
