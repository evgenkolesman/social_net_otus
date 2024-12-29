package ru.kolesnikov.social_net_otus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableConfigurationProperties
@SpringBootApplication
@EnableWebSecurity
class SocialNetOtusApplication

fun main(args: Array<String>) {
    runApplication<SocialNetOtusApplication>(*args)
}
