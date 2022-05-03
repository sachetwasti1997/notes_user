package com.sachet.notes_user.model

import org.jetbrains.annotations.NotNull

data class LoginRequest(
    @NotNull(value = "UserName cannot be null")
    val userName: String,
    @NotNull
    val password: String
)