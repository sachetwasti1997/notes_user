package com.sachet.notes_user.repository

import com.sachet.notes_user.model.User
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val reactiveMongoTemplate: ReactiveMongoTemplate,
) {

    suspend fun findUserByUserName(userName: String): User? {
        val query = Query(Criteria.where("userName").`is`(userName))
        return reactiveMongoTemplate.find(query, User::class.java).awaitFirstOrNull()
    }

    suspend fun findUserById(userId: String): User? {
        val query = Query(Criteria.where("userId").`is`(userId))
        return reactiveMongoTemplate.find(query, User::class.java).awaitFirstOrNull()
    }

    suspend fun findUserByEmail(email: String): User? {
        val query = Query(Criteria.where("email").`is`(email))
        return reactiveMongoTemplate.find(query, User::class.java).awaitFirstOrNull()
    }

    suspend fun saveUser(user: User): User {
        return reactiveMongoTemplate.save(user).awaitFirst()
    }

}