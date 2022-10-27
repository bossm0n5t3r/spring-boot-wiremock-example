package me.bossm0n5t3r.wiremock.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class FakeStoreStaticProperties {
    @Value("\${fake-store.api}")
    fun setApi(api: String) {
        FakeStoreStaticProperties.api = api
    }

    companion object {
        @JvmField
        var api: String = ""
    }
}
