package ru.kolesnikov.social_net_otus.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("sno_user_register")
data class UserRegisterEntity(
    @Id
    @Column("id")
     val iden: UUID? = null,
    @Column("username")
     val username: String? = null,
    @Column("first_name")
     val firstName: String? = null,
    @Column("second_name")
     val secondName: String? = null,

    @Column("birthdate")
     val birthdate: java.time.LocalDate? = null,

    @Column("biography")
     val biography: String? = null,

    @Column("city")
     val city: String? = null,

    @Column("password")
     val password: String? = null
) : Persistable<UUID?> {
    override fun getId(): UUID? = iden

    override fun isNew(): Boolean = true //TODO need update

}