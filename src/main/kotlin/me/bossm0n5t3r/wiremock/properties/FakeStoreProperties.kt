package me.bossm0n5t3r.wiremock.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("fake-store")
data class FakeStoreProperties(
    val api: String
)
