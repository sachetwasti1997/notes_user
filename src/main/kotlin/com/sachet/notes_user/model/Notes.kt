package com.sachet.notes_user.model

import java.time.LocalDateTime
import javax.validation.constraints.NotNull

data class Notes(
    private var noteId: String ?= null,
    private var title: @NotNull(message = "Title cannot be null!") String,
    private val description: @NotNull(message = "Description cannot be null") String,
    private val userId: @NotNull(message = "UserId cannot be null") String,
    private val color: Int,
    private var localDateTime: String ?= null
)
