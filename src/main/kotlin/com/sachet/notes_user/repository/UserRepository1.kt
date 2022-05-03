package com.sachet.notes_user.repository

import com.sachet.notes_user.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UserRepository1: ReactiveMongoRepository<User, String> {

}