package ru.kolesnikov.social_net_otus.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import ru.kolesnikov.social_net_otus.entity.DialogsManagementEntity
import java.util.*

interface DialogsManagementRepository : CrudRepository<DialogsManagementEntity, UUID> {

    @Query("""
        SELECT * FROM sno_dialogs
        WHERE ("from" = :currentLogin OR "to" = :currentLogin)
        AND ("from" = :userId OR "to" = :userId)
        ORDER BY time_modified DESC
    """)
    fun getDialogMessages(currentLogin: String, userId: String): List<DialogsManagementEntity>

    @Query("""
        INSERT INTO sno_dialogs("from", "to", "text")
         values (:userId, :currentLogin, :text) RETURNING id
    """)
    fun addDialogMessages(currentLogin: String, userId: String, text: String): UUID


}
