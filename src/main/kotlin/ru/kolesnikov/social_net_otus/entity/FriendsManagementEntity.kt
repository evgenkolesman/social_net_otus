package ru.kolesnikov.social_net_otus.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("sno_friends")
data class FriendsManagementEntity(
    @Id
    @Column("id")
    val id: Long,
    @Column("first_login")
    val firstLogin: String,
    @Column("second_login")
    val secondLogin: String,
    @Column("active")
    val active: Boolean = true,
)
