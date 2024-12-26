package ru.kolesnikov.social_net_otus.entity

import java.io.Serializable
import java.time.LocalDate
import java.util.*

/**
 * DTO for {@link ru.kolesnikov.social_net_otus.entity.UserRegisterEntity}
 */
data class UserRegisterEntityDto(
    val id: UUID? = null,
    val username: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val birthdate: LocalDate? = null,
    val biography: String? = null,
    val city: String? = null,
    val password: String? = null,
) : Serializable