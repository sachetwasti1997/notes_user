package com.sachet.notes_user.controller

import com.sachet.notes_user.model.Notes
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Mono
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/note")
class NotesRelatedService(
    private val webClient: WebClient
) {

    @GetMapping("/user")
    suspend fun getNotesByUserId(userIdAuth: Principal): ResponseEntity<Any> {
        return webClient
            .get()
            .uri("http://localhost:8081/notes/${userIdAuth.name}")
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange {
                val entity = it.awaitEntity<Any>()
//                delay(1000)
                entity
            }
    }

    @PostMapping("/save")
    suspend fun saveNotesOfUser(principal: Principal, @RequestBody @Valid notes: Mono<Notes>): Notes {
        val notesToSave = notes.awaitSingle()
        notesToSave.userId = principal.name
        println(notesToSave.userId)
        return webClient
            .post()
            .uri("http://localhost:8081/notes/save")
            .bodyValue(notesToSave)
            .awaitExchange {
                it.awaitBody()
            }
    }

    @GetMapping("/single/{noteId}")
    suspend fun getNotesById(@PathVariable noteId: String): ResponseEntity<Any> =
        webClient
            .get()
            .uri("http://localhost:8081/notes/single/$noteId")
            .awaitExchange {
                val body = it.awaitEntity<Any>()
                body
            }

    @DeleteMapping("/{noteId}")
    suspend fun deleteNoteById(@PathVariable noteId: String): ResponseEntity<Any> =
        webClient
            .delete()
            .uri("http://localhost:8081/notes/$noteId")
            .awaitExchange {
                val mess = it.awaitEntity<Any>()
                println(mess)
                mess
            }
}