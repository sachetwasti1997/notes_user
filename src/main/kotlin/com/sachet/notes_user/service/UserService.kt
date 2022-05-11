package com.sachet.notes_user.service

import com.sachet.notes_user.errors.UserNameAlreadyTaken
import com.sachet.notes_user.errors.UserNotFoundError
import com.sachet.notes_user.model.LoginRequest
import com.sachet.notes_user.repository.UserRepository1
import com.sachet.notes_user.model.User
import com.sachet.notes_user.repository.UserRepository
import com.sachet.notes_user.security.JsonWebTokenUtility
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jsonWebTokenUtility: JsonWebTokenUtility
) {

    suspend fun saveUser(user: User): User? {
        val userChecked = findUserByUserName(user.userName!!)
        if (userChecked != null){
            throw UserNameAlreadyTaken("User with the userName already exist")
        }
        user.password = bCryptPasswordEncoder.encode(user.password)
        return userRepository.saveUser(user)
    }

    private suspend fun findUserByUserName(userName: String): User? = userRepository.findUserByUserName(userName)

    suspend fun loginUser(loginRequest: LoginRequest): String{
        val user = userRepository.findUserByUserName(loginRequest.userName)
            ?: throw UserNotFoundError("No User Found: Invalid credentials")
        if(bCryptPasswordEncoder.matches(loginRequest.password, user.password)){
            return jsonWebTokenUtility.generateToken(user)
        }
        else throw UserNotFoundError("No User Found: Invalid credentials")
    }

    suspend fun findUserById(userId: String): User? {
        return userRepository.findUserById(userId)
    }

}