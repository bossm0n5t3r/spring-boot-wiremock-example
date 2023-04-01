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
import me.bossm0n5t3r.wiremock.application.DummyRestTemplateSupporter
import me.bossm0n5t3r.wiremock.application.DummyServiceWithStaticProperties
import me.bossm0n5t3r.wiremock.application.DummyWebClientSupporter
import me.bossm0n5t3r.wiremock.properties.FakeStoreStaticProperties
import me.bossm0n5t3r.wiremock.util.ResourceUtil.readFileAsJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@WireMockTest
internal class UsingWireMockRuntimeInfoTest {
    private val restTemplate = RestTemplate()
    private val objectMapper = jacksonObjectMapper()
    private val dummyRestTemplateSupporter = DummyRestTemplateSupporter(restTemplate, objectMapper)

    private lateinit var webClient: WebClient
    private lateinit var dummyWebClientSupporter: DummyWebClientSupporter
    private lateinit var sut: DummyServiceWithStaticProperties

    private fun setup(baseUrl: String) {
        FakeStoreStaticProperties.api = baseUrl
        webClient = WebClient.create(baseUrl)
        dummyWebClientSupporter = DummyWebClientSupporter(webClient, objectMapper)
        sut = DummyServiceWithStaticProperties(
            dummyRestTemplateSupporter = dummyRestTemplateSupporter,
            dummyWebClientSupporter = dummyWebClientSupporter,
        )
    }

    @Test
    fun getAllProductsUsingRestTemplateTest(wiremockRuntimeInfo: WireMockRuntimeInfo) {
        setup(wiremockRuntimeInfo.httpBaseUrl)
        stubFor(
            get(urlPathEqualTo("/products"))
                .willReturn(ok().withBody("products.json".readFileAsJson()))
        )

        val result = sut.getAllProductsUsingRestTemplate()

        assertThat(result).isNotEmpty
        verify(exactly(1), getRequestedFor(urlEqualTo("/products")))
    }

    @Test
    fun getAllProductsUsingWebClientTest(wiremockRuntimeInfo: WireMockRuntimeInfo) {
        setup(wiremockRuntimeInfo.httpBaseUrl)
        stubFor(
            get(urlPathEqualTo("/products"))
                .willReturn(ok().withBody("products.json".readFileAsJson()))
        )

        val result = sut.getAllProductsUsingWebClient()

        assertThat(result).isNotEmpty
        verify(exactly(1), getRequestedFor(urlEqualTo("/products")))
    }
}
