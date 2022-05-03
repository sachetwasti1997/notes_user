package com.sachet.notes_user.security

import com.sachet.notes_user.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

/**
 * This class is used to create a token and extract user information from token
 */
@Service
class JsonWebTokenUtility {

    private val SECRET_KEY = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzYWNoZXRfd2FzdGkxIiwiaWF0IjoxNjUxNTY2MjU5LCJleHAiOjE2NTE2MDIyNTl9.g1-bbXatc8ZXliQQkvHOHwHHt-9ny_Zbf-fcfSp4DLIYvr8EL4-H-7VrUyAbUb9H$2a$10RC2UTyJmSgT0gzWLJc39auH6flxqF1o1PZpaMJxq1RVnsnYSPXDw2"

//    private val SECRET_KEY = "abceyJhbGciOiJIUzM4NCJ9eyJzdWIiOiJzYWNoZXRfd2FzdGkxIiwiaWF0IjoxNjUxNTY2MjU5LCJleHAiOjE2NTE2MDIyNTl9"

    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun extractAllClaims(token: String?): Claims {
        val secretKey = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(userModel: User): String {
        val claims: Map<String, Any?> = HashMap()
        return createToken(claims, userModel)
    }

    private fun createToken(claims: Map<String, Any?>, userModel: User): String {
        val key = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userModel.userName)
            .claim("roles", userModel.roles)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(key).compact()
    }

    fun validateToken(token: String?, userModel: User): Boolean {
        val username = extractUsername(token)
        println("$username username")
        println("$userModel validate")
        println(username == userModel.userName)
        println(!isTokenExpired(token))
        return username == userModel.userName && !isTokenExpired(token)
    }
}