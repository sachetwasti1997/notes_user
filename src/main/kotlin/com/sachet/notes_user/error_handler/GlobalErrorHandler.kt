package com.sachet.notes_user.error_handler

import com.sachet.notes_user.errors.ApiError
import com.sachet.notes_user.errors.UserNameAlreadyTaken
import com.sachet.notes_user.errors.UserNotFoundError
import com.sachet.notes_user.errors.UserUnauthorisedException
import com.sachet.notes_user.model.LoginResponse
import com.sachet.notes_user.model.SignUpResponse
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.support.WebExchangeBindException
import java.util.stream.Collectors

@ControllerAdvice
class GlobalErrorHandler {

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleWebExchangeBindException(ex: WebExchangeBindException): ResponseEntity<ApiError>{
        val message = ex.bindingResult.allErrors.stream()
            .map ( DefaultMessageSourceResolvable::getDefaultMessage )
            .collect(Collectors.joining("|"))

        return ResponseEntity.ok().body(ApiError(message, 400))
    }

    @ExceptionHandler(UserNotFoundError::class)
    fun handleUserNotFoundError(ex: UserNotFoundError): ResponseEntity<LoginResponse> = ResponseEntity.ok().body(LoginResponse(token = null, exception = ex.message, code = 404))

    @ExceptionHandler(UserNameAlreadyTaken::class)
    fun handleUserNameAlreadyTaken(ex: UserNameAlreadyTaken): ResponseEntity<SignUpResponse> = ResponseEntity.ok().body(SignUpResponse(message = ex.message, code = 400))

    @ExceptionHandler(UserUnauthorisedException::class)
    fun handleExpiredJwtException(ex: UserUnauthorisedException): ResponseEntity<Any>{
        println("UNAUTHORISED")
        return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

}