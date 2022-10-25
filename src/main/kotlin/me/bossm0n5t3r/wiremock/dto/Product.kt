package me.bossm0n5t3r.wiremock.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val category: String,
    val description: String,
    val image: String,
)
