package ru.kolesnikov.social_net_otus

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<SocialNetOtusApplication>().with(TestcontainersConfiguration::class).run(*args)
}
