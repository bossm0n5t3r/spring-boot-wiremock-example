package me.bossm0n5t3r.wiremock.application

import me.bossm0n5t3r.wiremock.dto.Product
import me.bossm0n5t3r.wiremock.properties.FakeStoreProperties
import org.springframework.stereotype.Service

@Service
class DummyServiceWithNonStaticProperties(
    private val fakeStoreProperties: FakeStoreProperties,
    private val dummyRestTemplateSupporter: DummyRestTemplateSupporter,
    private val dummyWebClientSupporter: DummyWebClientSupporter,
) {
    private val url = "${fakeStoreProperties.api}/products"

    fun getAllProductsUsingRestTemplate(): List<Product> {
        return dummyRestTemplateSupporter.getAllProducts(url)
    }

    fun getAllProductsUsingWebClient(): List<Product> {
        return dummyWebClientSupporter.getAllProducts(url)
    }
}
