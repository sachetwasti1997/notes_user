package com.sachet.notes_user.controller

import com.sachet.notes_user.model.Notes
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/note")
class NotesRelatedService(
    private val webClient: WebClient
) {

    @GetMapping("/user")
    suspend fun getNotesByUserId(userIdAuth: Principal): List<Notes> = webClient
            .get()
            .uri("http://localhost:8081/notes/${userIdAuth.name}")
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange {
                it.awaitBody()
            }

    @PostMapping("/save")
    suspend fun saveNotesOfUser(@RequestBody @Valid notes: Mono<Notes>): Notes =
        webClient
                .post()
                .uri("http://localhost:8081/notes/save")
                .body(notes)
                .awaitExchange {
                    it.awaitBody()
                }

    @GetMapping("/single/{noteId}")
    suspend fun getNotesById(@PathVariable noteId: String): ResponseEntity<Any> =
        webClient
            .get()
            .uri("http://localhost:8081/notes/single/$noteId")
            .awaitExchange {
                val status = it.statusCode()
                if (status.is4xxClientError){
                    throw Exception(status.reasonPhrase)
                }
                if (status.is5xxServerError){
                    throw Exception(status.reasonPhrase)
                }
                val body = it.awaitEntity<Any>()
                body
            }

    @DeleteMapping("/{noteId}")
    suspend fun deleteNoteById(@PathVariable noteId: String): String =
        webClient
            .delete()
            .uri("http://localhost:8081/notes/$noteId")
            .awaitExchange {
                it.awaitBody()
            }
}