package ru.kolesnikov.social_net_otus.model

import java.io.Serializable
import java.util.*

/**
 * DTO for {@link ru.kolesnikov.social_net_otus.entity.DialogsManagementEntity}
 */
data class DialogsManagementEntityDto(
    val id: UUID? = null,
    val from: String? = null,
    val to: String? = null,
    val text: String? = null,
) : Serializable