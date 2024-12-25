package ru.kolesnikov.social_net_otus.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("sno_dialogs")
data class DialogsManagementEntity(
    @Id
    @Column("id")
    private val id: UUID?,
    @Column("from")
    private val from: String,
    @Column("to")
    private val to: String,
    @Column("text")
    private val text: String,
    @Transient
    private var isNew: Boolean,

    ) : Persistable<UUID> {
    override fun getId(): UUID? = id

    override fun isNew(): Boolean = isNew

    fun markNotNew() {
        isNew = false
    }

}
