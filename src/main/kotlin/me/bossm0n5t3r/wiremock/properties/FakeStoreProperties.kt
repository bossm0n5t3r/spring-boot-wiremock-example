package me.bossm0n5t3r.wiremock.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("fake-store")
data class FakeStoreProperties(
    val api: String
)
