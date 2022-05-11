package com.sachet.notes_user.model

data class LoginResponse(
    var token: String ?= null,
    var exception: String ?= null,
    var code: Int ?= null
)