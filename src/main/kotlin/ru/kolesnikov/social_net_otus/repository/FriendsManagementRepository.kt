package ru.kolesnikov.social_net_otus.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import ru.kolesnikov.social_net_otus.entity.FriendsManagementEntity
import java.util.*

interface FriendsManagementRepository : CrudRepository<FriendsManagementEntity, Long> {

    @Query(
        """
        UPDATE sno_friends
        SET active = false
        WHERE (first_login = :currentLogin OR second_login = :currentLogin) 
        AND (first_login = :userId OR second_login = :userId) 
        AND active = true
    """
    )
    fun deleteFriendPair(currentLogin: String, userId: String)

    @Query(
        """
        SELECT 1 FROM sno_friends
        WHERE (first_login = :currentLogin OR second_login = :currentLogin) 
        AND (first_login = :userId OR second_login = :userId) 
        AND active = true
    """
    )
    fun areFriends(currentLogin: String, userId: String): Boolean

    @Query(
        """
        INSERT INTO sno_friends(first_login, second_login, active ) VALUES (:currentLogin, :userId, true)
    """
    )
    fun addFriendToCurrentUser(currentLogin: String, userId: String)


}
