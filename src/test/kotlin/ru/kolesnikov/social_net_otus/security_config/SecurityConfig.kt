package ru.kolesnikov.social_net_otus.security_config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer

import org.springframework.test.context.ActiveProfiles


@ActiveProfiles("test")
@Configuration
class SecurityConfig {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().anyRequest() }
    }
}