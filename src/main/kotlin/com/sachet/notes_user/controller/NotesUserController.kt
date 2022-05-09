package com.sachet.notes_user.controller

import com.fasterxml.jackson.annotation.JsonView
import com.sachet.notes_user.model.LoginRequest
import com.sachet.notes_user.model.User
import com.sachet.notes_user.model.Views
import com.sachet.notes_user.service.UserService
import kotlinx.coroutines.reactor.awaitSingle
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

    @PostMapping
    @JsonView(Views.Base::class)
    suspend fun signUp(@RequestBody @Valid userMono: Mono<User>): User?{
        val user = userMono.awaitSingle()
        return userService.saveUser(user)
    }

    @PostMapping("/login")
    suspend fun login(@RequestBody @Valid loginRequest: LoginRequest) = userService.loginUser(loginRequest)

    @GetMapping("/profile")
    suspend fun getUser(principal: Principal) = userService.findUserById(principal.name)

}