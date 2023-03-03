package me.bossm0n5t3r.wiremock.application

import com.fasterxml.jackson.databind.ObjectMapper
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
import me.bossm0n5t3r.wiremock.common.AbstractSpringBootTest
import me.bossm0n5t3r.wiremock.properties.FakeStoreProperties
import me.bossm0n5t3r.wiremock.util.ResourceUtil.readFileAsJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.client.RestTemplate

@WireMockTest
internal class DummyServiceWithNonStaticPropertiesWithSpringBootTest @Autowired private constructor(
    restTemplate: RestTemplate,
    objectMapper: ObjectMapper,
) : AbstractSpringBootTest() {
    private val fakeStoreWiremockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())
    private val fakeStoreProperties = mockk<FakeStoreProperties>()
    private val sut = DummyServiceWithNonStaticProperties(
        restTemplate,
        objectMapper,
        fakeStoreProperties
    )

    @BeforeEach
    fun setup() {
        fakeStoreWiremockServer.start()
    }

    @AfterEach
    fun cleanUp() {
        fakeStoreWiremockServer.stop()
    }

    @Test
    fun getAllProductsTest() {
        fakeStoreWiremockServer.stubFor(
            get(urlPathEqualTo("/products"))
                .willReturn(ok().withBody("products.json".readFileAsJson()))
        )
        every { fakeStoreProperties.api } returns fakeStoreWiremockServer.baseUrl()

        val result = sut.getAllProducts()

        assertThat(result).isNotEmpty
        fakeStoreWiremockServer.verify(exactly(1), getRequestedFor(urlEqualTo("/products")))
        fakeStoreWiremockServer.stop()
    }
}
