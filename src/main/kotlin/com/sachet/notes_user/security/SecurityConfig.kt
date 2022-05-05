package com.sachet.notes_user.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val authenticationConverter: AuthenticationConverter
) {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun springSecurityFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain
    = serverHttpSecurity
        .authorizeExchange()
        .pathMatchers(HttpMethod.POST, "/v1/notes/user").permitAll()
        .pathMatchers(HttpMethod.POST, "/v1/notes/user/login").permitAll()
        .pathMatchers(HttpMethod.GET, "/v1/notes/user/{userId}").hasRole("USER")
        .pathMatchers(HttpMethod.GET, "/v1/notes/user/me").hasRole("USER")
        .pathMatchers(HttpMethod.GET, "/note/user/{userId}").hasRole("USER")
        .pathMatchers(HttpMethod.POST, "/note/save").hasRole("USER")
        .pathMatchers(HttpMethod.GET, "/note/single/{noteId}").hasRole("USER")
        .pathMatchers(HttpMethod.DELETE, "/note/{noteId}").hasRole("USER")
        .and()
        .httpBasic().disable()
        .formLogin().disable()
        .csrf().disable()
        .authenticationManager(authenticationManager)
        .securityContextRepository(authenticationConverter)
        .build()
}