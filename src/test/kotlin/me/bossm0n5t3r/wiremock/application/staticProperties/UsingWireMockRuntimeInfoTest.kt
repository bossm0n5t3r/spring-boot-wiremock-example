package me.bossm0n5t3r.wiremock.application.staticProperties

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock.exactly
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import me.bossm0n5t3r.wiremock.application.DummyServiceWithStaticProperties
import me.bossm0n5t3r.wiremock.properties.FakeStoreStaticProperties
import me.bossm0n5t3r.wiremock.util.ResourceUtil.readFileAsJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

@WireMockTest
internal class UsingWireMockRuntimeInfoTest {
    private val restTemplate = RestTemplate()
    private val objectMapper = jacksonObjectMapper()
    private val sut = DummyServiceWithStaticProperties(
        restTemplate,
        objectMapper,
    )

    @Test
    fun getAllProductsTest(wiremockRuntimeInfo: WireMockRuntimeInfo) {
        stubFor(
            get(urlPathEqualTo("/products"))
                .willReturn(ok().withBody("products.json".readFileAsJson()))
        )
        FakeStoreStaticProperties.api = wiremockRuntimeInfo.httpBaseUrl

        val result = sut.getAllProducts()

        assertThat(result).isNotEmpty
        verify(exactly(1), getRequestedFor(urlEqualTo("/products")))
    }
}
