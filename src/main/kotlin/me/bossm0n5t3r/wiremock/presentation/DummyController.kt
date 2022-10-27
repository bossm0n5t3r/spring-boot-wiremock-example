package me.bossm0n5t3r.wiremock.presentation

import me.bossm0n5t3r.wiremock.application.DummyService
import me.bossm0n5t3r.wiremock.dto.Product
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DummyController(
    private val dummyService: DummyService
) {
    @GetMapping("/products")
    fun getAllProducts(): List<Product> {
        return dummyService.getAllProducts()
    }
}
