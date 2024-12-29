package ru.kolesnikov.social_net_otus.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import ru.kolesnikov.social_net_otus.entity.UserRegisterEntity
import java.util.*

interface UserRegisterRepository : CrudRepository<UserRegisterEntity, UUID?> {
    @Query(
        """
        SELECT * FROM social_net_otus.sno_user_register where user_id = :userName LIMIT 1
    """
    )
    fun findByUsername(userName: String): Optional<UserRegisterEntity>

    @Query(
        """
        SELECT * FROM social_net_otus.sno_user_register where first_name = :firstName AND second_name = :lastName
    """
    )
    fun findByFirstNameAndLastName(firstName: String, secondName: String): List<UserRegisterEntity>
}