package me.bossm0n5t3r.wiremock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SpringBootWiremockExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringBootWiremockExampleApplication>(*args)
}
