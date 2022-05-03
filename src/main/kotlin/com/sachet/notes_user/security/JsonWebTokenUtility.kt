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

    private val SECRET_KEY = "$2a$10MvvxNxkiYLBvzVu97APmQ.UA3rm.qwQIRWizYVOYG7gAFWMOnK/q6"

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
        return username == userModel.userName && !isTokenExpired(token)
    }
}