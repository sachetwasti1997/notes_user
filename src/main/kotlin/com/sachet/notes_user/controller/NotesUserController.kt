package com.sachet.notes_user.controller

import com.fasterxml.jackson.annotation.JsonView
import com.sachet.notes_user.model.*
import com.sachet.notes_user.service.UserService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/v1/notes/user")
class NotesUserController(
    private val userService: UserService
) {

    @PostMapping("/save")
    suspend fun signUp(@RequestBody @Valid userMono: Mono<User>): ResponseEntity<SignUpResponse>{
        val user = userMono.awaitSingle()
        userService.saveUser(user)
        return ResponseEntity.ok().body(SignUpResponse(message = "User Created Successfully!", code = 200))
    }

    @PostMapping("/login")
    suspend fun login(@RequestBody @Valid loginRequest: LoginRequest): ResponseEntity<LoginResponse>{
        val token = userService.loginUser(loginRequest)
        return ResponseEntity.ok().body(LoginResponse(token = token, code = 200))
    }

    @GetMapping("/profile")
    suspend fun getUser(principal: Principal) = userService.findUserById(principal.name)

}