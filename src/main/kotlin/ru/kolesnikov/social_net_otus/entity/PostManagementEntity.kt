package ru.kolesnikov.social_net_otus.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.*

@Table("sno_posts")
data class PostManagementEntity(
    @Id
    @Column("id")
     val iden: UUID? = null,
    @Column("login")
     val login: String,
    @Column("text_post")
     val text: String,
    @Column("time_modified")
    val timeModified: Instant = Instant.now(),
    @Column("active")
    val active: Boolean = true,

    ) : Persistable<UUID> {

    override fun getId(): UUID? = iden
    override fun isNew(): Boolean = iden == null


}
