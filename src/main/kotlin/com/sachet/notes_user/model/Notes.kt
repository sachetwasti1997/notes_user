package com.sachet.notes_user.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.NotNull

data class Notes(
    var noteId: String ?= null,
    @field:NotNull(message = "Title cannot be null!")
    val title: String ?= null,
    @field:NotNull(message = "Description cannot be null")
    val description: String ?= null,
    @field:NotNull(message = "UserId cannot be null")
    val userId:  String ?= null,
    val color: Int ?= null,
    var localDateTime: String ?= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss"))
)
