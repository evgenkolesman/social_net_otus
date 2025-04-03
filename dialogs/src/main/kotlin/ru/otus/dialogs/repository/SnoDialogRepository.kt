package ru.otus.dialogs.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.ListCrudRepository
import java.util.UUID

interface SnoDialogRepository :ListCrudRepository<SnoDialogEntity, UUID?> {

// TODO Add pagination
    @Query(
        """
            SELECT * FROM dialog.dialogs.sno_dialogs
            WHERE "from" = :from AND "to" = :to
            LIMIT 100
        """
    )
    fun findSnoDialogEntitiesByFromAndTo(from: String, to: String): List<SnoDialogEntity>
}