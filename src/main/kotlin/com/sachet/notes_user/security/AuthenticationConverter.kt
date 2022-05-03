package com.sachet.notes_user.security

import com.sachet.notes_user.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationConverter(
    private val authenticationManager: AuthenticationManager,
    private val jsonWebTokenUtility: JsonWebTokenUtility,
    private val repository: UserRepository
): ServerSecurityContextRepository {
    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        return Mono.empty()
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        return Mono.justOrEmpty(exchange!!.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
            .filter {
                it.startsWith("Bearer ")
            }
            .map {
                it.substring("Bearer ".length)
            }
            .flatMap {
                mono { createSecurityContext(it) }
            }
    }

    suspend fun createSecurityContext(token: String): SecurityContext{
        val userName: String = jsonWebTokenUtility.extractUsername(token)
        val user = repository.findUserByUserName(userName)
        val authorities = ArrayList<SimpleGrantedAuthority>()
        if (user?.roles != null){
            user.roles?.forEach {
                authorities.add(SimpleGrantedAuthority(it))
            }
        }
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
            user?.userId,
            token,
            authorities
        )
        return SecurityContextImpl(authenticationManager.authenticate(usernamePasswordAuthenticationToken).awaitSingleOrNull())
    }
}