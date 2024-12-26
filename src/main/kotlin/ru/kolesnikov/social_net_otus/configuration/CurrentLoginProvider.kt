package ru.kolesnikov.social_net_otus.configuration

import java.util.*

interface CurrentLoginProvider {
    fun getCurrentLogin(): Optional<String?>?
    fun getCurrentPassword(): Optional<String?>?

}