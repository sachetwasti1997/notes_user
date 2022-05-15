package com.sachet.notes_user.webFilter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DataBufferWriter {
    private val objectMapper: ObjectMapper? = null
    fun <T> write(httpResponse: ServerHttpResponse, `object`: T): Mono<Void> {
        return httpResponse
            .writeWith(Mono.fromSupplier {
                val bufferFactory: DataBufferFactory = httpResponse.bufferFactory()
                try {
                    return@fromSupplier bufferFactory.wrap(objectMapper!!.writeValueAsBytes(`object`))
                } catch (ex: Exception) {
                    return@fromSupplier bufferFactory.wrap(ByteArray(0))
                }
            })
    }
}