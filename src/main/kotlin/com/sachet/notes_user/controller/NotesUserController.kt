package com.sachet.notes_user.controller

import com.sachet.notes_user.model.User
import com.sachet.notes_user.service.UserService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/v1/notes/user")
class NotesUserController(
    private val userService: UserService
) {

    @PostMapping
    suspend fun signUp(@RequestBody @Valid userMono: Mono<User>): User?{
        val user = userMono.awaitSingle()
        return userService.saveUser(user)
    }


}