package org.ae.app.config


import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.handler.HandlerMappingIntrospector


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${spring.security.oauth2.resource server.jwt.issuer-uri}")
    private lateinit var issuerUri: String


    @Bean
    fun mvc(introspector: HandlerMappingIntrospector): MvcRequestMatcher.Builder {
        return MvcRequestMatcher.Builder(introspector);
    }


    @Bean
    fun httpFilterChainConfig(
        http: HttpSecurity,
        mvc: MvcRequestMatcher.Builder,
        @Qualifier("handlerExceptionResolver") resolver: HandlerExceptionResolver
    ): SecurityFilterChain {

        http.authorizeHttpRequests { authorizeRequests ->
            authorizeRequests
                .requestMatchers(*Constants.WHITELIST.map(mvc::pattern).toTypedArray()).permitAll()
                .requestMatchers(mvc.pattern("/actuator/**")).hasRole(ROLES.ADMIN)
                .requestMatchers(mvc.pattern(Constants.API_VERSION)).authenticated()
                .anyRequest()
                .authenticated()
        }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt {
                    it.jwtAuthenticationConverter(JwtAuthenticationConverter())
                    it.decoder(JwtDecoders.fromIssuerLocation(issuerUri))
                }
                    .authenticationEntryPoint { request: HttpServletRequest, response: HttpServletResponse, authException ->
                        resolver.resolveException(request, response, null, authException)
                    }

            }.sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return http.build()
    }

}


