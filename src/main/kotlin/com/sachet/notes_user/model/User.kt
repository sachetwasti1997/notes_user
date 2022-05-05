package com.sachet.notes_user.model

import com.fasterxml.jackson.annotation.JsonView
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    @JsonView(Views.Base::class)
    var userId: String? = null,
    @field:NotNull(value = "First Name cannot be null")
    @JsonView(Views.Base::class)
    var firstName: String? = null,
    @JsonView(Views.Base::class)
    var middleName: String? = null,
    @field:NotNull(value = "Last Name cannot be null")
    @JsonView(Views.Base::class)
    var lastName: String? = null,
    @field:NotNull(value = "Last Name cannot be null")
    @JsonView(Views.Base::class)
    var email: String? = null,
    @field:NotNull(value = "User Name cannot be null")
    @JsonView(Views.Base::class)
    var userName: String? = null,
    @field:NotNull(value = "Password cannot be null")
    var password: String? = null,
    var roles: List<String> ?= null
)
