package ru.otus.dialogs.repository

import org.springframework.data.repository.ListCrudRepository
import java.util.UUID

interface SnoDialogRepository :ListCrudRepository<SnoDilaogEntity, UUID?> {

    fun findAllByFrom(from: String): MutableList<SnoDilaogEntity>
}