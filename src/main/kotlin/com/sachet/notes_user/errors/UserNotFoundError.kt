package com.sachet.notes_user.errors

class UserNotFoundError(private val errorMessage: String): RuntimeException(errorMessage)