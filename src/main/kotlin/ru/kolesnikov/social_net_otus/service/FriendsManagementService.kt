package ru.kolesnikov.social_net_otus.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.repository.FriendsManagementRepository

@Service
class FriendsManagementService(private val currentLoginProvider: CurrentLoginProvider,
    private val friendsManagementRepository: FriendsManagementRepository
    ) {

    @Transactional(readOnly = true)
    fun friendDeleteUserId(userId: String) {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        friendsManagementRepository.deleteFriendPair(currentLogin, userId)
    }

    @Transactional(readOnly = true)
    fun friendSetUserId(userId: String) {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        if(friendsManagementRepository.areFriends(currentLogin, userId)) return
        else friendsManagementRepository.addFriendToCurrentUser(currentLogin, userId)
    }
}
