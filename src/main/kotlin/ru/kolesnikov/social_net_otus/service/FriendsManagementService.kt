package ru.kolesnikov.social_net_otus.service

import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider

@Service
class FriendsManagementService(private val currentLoginProvider: CurrentLoginProvider) {
    fun friendDeleteUserId(userId: String) {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        println(" LOGIN " + currentLogin)


    }

    fun friendSetUserId(userId: String) {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        TODO("Not yet implemented")
    }
}
