package com.sachet.notes_user.model

import com.fasterxml.jackson.annotation.JsonView

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotNull

@Document
data class User(
    @Id
    @JsonView(Views.Base::class)
    var userId: String? = null,
    @field:NotNull(message = "First Name cannot be null")
    @JsonView(Views.Base::class)
    var firstName: String? = null,
    @JsonView(Views.Base::class)
    var middleName: String? = null,
    @field:NotNull(message = "Last Name cannot be null")
    @JsonView(Views.Base::class)
    var lastName: String? = null,
    @field:NotNull(message = "Last Name cannot be null")
    @JsonView(Views.Base::class)
    var email: String? = null,
    @field:NotNull(message = "User Name cannot be null")
    @JsonView(Views.Base::class)
    var userName: String? = null,
    @field:NotNull(message = "Password cannot be null")
    var password: String? = null,
    var roles: List<String> ?= listOf("ROLE_USER")
)
