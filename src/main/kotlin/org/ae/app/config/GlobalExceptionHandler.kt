package org.ae.app.config


import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(AuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthenticationException(ex: AuthenticationException, request: WebRequest): ProblemDetail {
        log.error("Authentication error at {}: {}", request.getDescription(false), ex.message)
        val problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED)
        problemDetail.detail = String.format("Authentication error  %s", ex.message)
        problemDetail.title = "Authentication error"
        return problemDetail
    }

    @ExceptionHandler(JwtException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleJwtException(ex: JwtException, request: WebRequest): ProblemDetail {
        log.error("JWT error at {}: {}", request.getDescription(false), ex.message)
        val problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED)
        problemDetail.detail = String.format("JWT error  %s", ex.message)
        problemDetail.title = "JWT error"
        return problemDetail
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception, request: WebRequest): ProblemDetail {
        log.error("Error at {}: {}", request.getDescription(false), ex.message)
        val problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        problemDetail.detail = String.format("Error  %s", ex.message)
        problemDetail.title = "Error"
        return problemDetail
    }

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: WebRequest): ProblemDetail {
        log.error("Access denied at {}: {}", request.getDescription(false), ex.message)
        val problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN)
        problemDetail.detail = String.format("Access denied  %s", ex.message)
        problemDetail.title = "Access denied"
        return problemDetail
    }

}