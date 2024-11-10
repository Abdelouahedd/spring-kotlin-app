package org.ae.app.web

import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class GlobalController {

    private val log = LoggerFactory.getLogger(GlobalController::class.java)

    @GetMapping("/public/hello")
    fun hello(): String {
        log.info("Hello")
        return "Hello"
    }


    @GetMapping("/private/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun helloAdmin(): String {
        return "Hello Admin, you are authorized to access this resource 👌🏽!"
    }

}