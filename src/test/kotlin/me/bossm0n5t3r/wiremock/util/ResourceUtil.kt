package me.bossm0n5t3r.wiremock.util

import org.springframework.core.io.ClassPathResource

object ResourceUtil {
    fun String.readFileAsJson(): String {
        return ClassPathResource(this)
            .inputStream
            .bufferedReader()
            .readLines()
            .joinToString("\n")
    }
}
