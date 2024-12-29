package ru.kolesnikov.social_net_otus.configuration

interface CurrentLoginProvider {
    fun getCurrentLogin(): String

}