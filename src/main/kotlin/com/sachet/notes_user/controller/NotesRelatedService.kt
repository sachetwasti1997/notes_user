package com.sachet.notes_user.controller

import com.sachet.notes_user.model.Notes
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/note")
class NotesRelatedService(
    private val webClient: WebClient
) {

    @GetMapping("/user/{userId}")
    fun getNotesByUserId(@PathVariable userId: String): Flux<Notes> {
            return webClient
                .get()
                .uri("http://localhost:8081/notes/$userId")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux<Notes>()
                .doOnError {
                    throw it
                }
    }

    @PostMapping("/save")
    fun saveNotesOfUser(@RequestBody @Valid notes: Mono<Notes>): Mono<Notes>{
            return webClient
                .post()
                .uri("http://localhost:8081/notes/save")
                .body(notes)
                .retrieve()
                .bodyToMono<Notes>()
                .doOnError {
                    println(it.message)
                    throw it
                }
    }

}