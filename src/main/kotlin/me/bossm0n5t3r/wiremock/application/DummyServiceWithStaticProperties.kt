package me.bossm0n5t3r.wiremock.application

import me.bossm0n5t3r.wiremock.dto.Product
import me.bossm0n5t3r.wiremock.properties.FakeStoreStaticProperties
import org.springframework.stereotype.Service

@Service
class DummyServiceWithStaticProperties(
    private val dummyRestTemplateSupporter: DummyRestTemplateSupporter,
    private val dummyWebClientSupporter: DummyWebClientSupporter,
) {
    private val url = "${FakeStoreStaticProperties.api}/products"

    fun getAllProductsUsingRestTemplate(): List<Product> {
        return dummyRestTemplateSupporter.getAllProducts(url)
    }

    fun getAllProductsUsingWebClient(): List<Product> {
        return dummyWebClientSupporter.getAllProducts(url)
    }
}
