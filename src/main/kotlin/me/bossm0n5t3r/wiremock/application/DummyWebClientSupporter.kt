package me.bossm0n5t3r.wiremock.application

import com.fasterxml.jackson.databind.ObjectMapper
import me.bossm0n5t3r.wiremock.configuration.readValueWithTypeReference
import me.bossm0n5t3r.wiremock.dto.Product
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI

@Component
class DummyWebClientSupporter(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
) {
    fun getAllProducts(url: String): List<Product> {
        return try {
            webClient
                .get()
                .uri(URI(url))
                .retrieve()
                .onStatus(
                    { status: HttpStatusCode -> status.is4xxClientError || status.is5xxServerError },
                ) {
                    error(it)
                }
                .bodyToMono(String::class.java)
                .block()
                ?.let { objectMapper.readValueWithTypeReference<List<Product>>(it) }
                ?: emptyList()
        } catch (e: Exception) {
            error(e)
        }
    }
}
