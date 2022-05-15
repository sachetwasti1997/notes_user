package com.sachet.notes_user.webFilter

import com.fasterxml.jackson.databind.ObjectWriter
import com.sachet.notes_user.errors.UserUnauthorisedException
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import kotlin.reflect.typeOf

@Component
class GlobalWebFilter(
    val dataBufferWriter: DataBufferWriter
): ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex is UserUnauthorisedException){
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            val ret = dataBufferWriter.write(exchange.response, byteArrayOf())
            println(exchange.response)
            return ret
        }
        val ret = dataBufferWriter.write(exchange.response, byteArrayOf())
        println(exchange.response)
        return ret
    }
}