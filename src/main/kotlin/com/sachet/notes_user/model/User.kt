package com.sachet.notes_user.model

import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    var userId: String? = null,
    @NotNull(value = "First Name cannot be null")
    var firstName: String? = null,
    var middleName: String? = null,
    @NotNull(value = "Last Name cannot be null")
    var lastName: String? = null,
    @NotNull(value = "Last Name cannot be null")
    var email: String? = null,
    @NotNull(value = "User Name cannot be null")
    var userName: String? = null,
    @NotNull(value = "Password cannot be null")
    var password: String? = null,
    var roles: List<String> ?= null
)
