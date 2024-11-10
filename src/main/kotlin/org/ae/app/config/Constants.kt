package org.ae.app.config

class Constants {
    companion object {
        const val API_VERSION = "/api/v1"
        val WHITELIST = arrayOf(
            "/swagger-ui.html/**",
            "/public/**"
        )
    }
}

class ROLES {
    companion object {
        const val ADMIN = "ADMIN"
    }
}