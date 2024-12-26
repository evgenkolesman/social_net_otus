package ru.kolesnikov.social_net_otus.entity

import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("sno_user_register")
data class UserRegisterEntity(
    @Column("id")
    private val id: UUID? = null,
    @Column("username")
    private val username: String? = null,
    @Column("name")
    private val firstName: String? = null,
    @Column("second_name")
    private val secondName: String? = null,

    @Column("birthdate")
    private val birthdate: java.time.LocalDate? = null,

    @Column("biography")
    private val biography: String? = null,

    @Column("city")
    private val city: String? = null,

    @Column("password")
    private val password: String? = null,
    @Transient
    private var isNew: Boolean = true
) : Persistable<UUID?> {
    override fun getId(): UUID? = id

    override fun isNew(): Boolean = isNew

    fun markNotNew() {
        isNew = false
    }

}