package me.bossm0n5t3r.wiremock.application.nonStaticProperties

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.exactly
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.mockk.every
import io.mockk.mockk
import me.bossm0n5t3r.wiremock.application.DummyRestTemplateSupporter
import me.bossm0n5t3r.wiremock.application.DummyServiceWithNonStaticProperties
import me.bossm0n5t3r.wiremock.application.DummyWebClientSupporter
import me.bossm0n5t3r.wiremock.properties.FakeStoreProperties
import me.bossm0n5t3r.wiremock.util.ResourceUtil.readFileAsJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@WireMockTest
internal class UsingWireMockServerTest {
    private val restTemplate = RestTemplate()
    private val objectMapper = jacksonObjectMapper()
    private val dummyRestTemplateSupporter = DummyRestTemplateSupporter(restTemplate, objectMapper)
    private val fakeStoreProperties = mockk<FakeStoreProperties>()

    private lateinit var webClient: WebClient
    private lateinit var dummyWebClientSupporter: DummyWebClientSupporter
    private lateinit var sut: DummyServiceWithNonStaticProperties

    private val fakeStoreWiremockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())

    @BeforeEach
    fun setup() {
        fakeStoreWiremockServer.start()

        every { fakeStoreProperties.api } returns fakeStoreWiremockServer.baseUrl()
        webClient = WebClient.create(fakeStoreWiremockServer.baseUrl())
        dummyWebClientSupporter = DummyWebClientSupporter(webClient, objectMapper)
        sut = DummyServiceWithNonStaticProperties(
            fakeStoreProperties = fakeStoreProperties,
            dummyRestTemplateSupporter = dummyRestTemplateSupporter,
            dummyWebClientSupporter = dummyWebClientSupporter,
        )
    }

    @AfterEach
    fun cleanUp() {
        fakeStoreWiremockServer.stop()
    }

    @Test
    fun getAllProductsUsingRestTemplateTest() {
        fakeStoreWiremockServer.stubFor(
            get(urlPathEqualTo("/products"))
                .willReturn(ok().withBody("products.json".readFileAsJson()))
        )

        val result = sut.getAllProductsUsingRestTemplate()

        assertThat(result).isNotEmpty
        fakeStoreWiremockServer.verify(exactly(1), getRequestedFor(urlEqualTo("/products")))
    }
}
