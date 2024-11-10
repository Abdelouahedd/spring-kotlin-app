package org.ae.app.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor


@Component
class SecurityRequestInterceptor : HandlerInterceptor {

    private val log = LoggerFactory.getLogger(SecurityRequestInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val url: String = request.requestURI
        val method: String = request.method
        val remoteAddr: String = request.remoteAddr
        val user: String = SecurityContextHolder.getContext().authentication.name

        log.info("Request: $method $url from $remoteAddr by $user")
        return true
    }
}