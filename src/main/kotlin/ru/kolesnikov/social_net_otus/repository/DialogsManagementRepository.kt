package ru.kolesnikov.social_net_otus.repository

import org.springframework.data.repository.CrudRepository
import ru.kolesnikov.social_net_otus.entity.DialogsManagementEntity
import java.util.*

interface DialogsManagementRepository : CrudRepository<DialogsManagementEntity, UUID>