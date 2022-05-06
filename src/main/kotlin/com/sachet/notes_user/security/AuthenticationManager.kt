package com.sachet.notes_user.security

import com.sachet.notes_user.repository.UserRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(
    private val jsonWebTokenUtility: JsonWebTokenUtility,
    private val repository: UserRepository
): ReactiveAuthenticationManager {

    suspend fun createAuthentication(authentication: Authentication?): Authentication?{
        val token: String? = authentication!!.credentials as String?
        val userName = jsonWebTokenUtility.extractUsername(token)
        val user = repository.findUserByUserName(userName) ?: throw Exception("User Not Found")
        return if (jsonWebTokenUtility.validateToken(token, userModel = user)){
            authentication
        }else{
            null
        }
    }

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        return if (authentication != null){
            mono { createAuthentication(authentication) }
        }else{
            Mono.empty()
        }
    }

}