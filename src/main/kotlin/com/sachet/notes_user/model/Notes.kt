package com.sachet.notes_user.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.NotNull

data class Notes(
    var noteId: String ?= null,
    var title: @NotNull(message = "Title cannot be null!") String ?= null,
    val description: @NotNull(message = "Description cannot be null") String ?= null,
    val userId: @NotNull(message = "UserId cannot be null") String ?= null,
    val color: Int ?= null,
    var localDateTime: String ?= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss"))
)
