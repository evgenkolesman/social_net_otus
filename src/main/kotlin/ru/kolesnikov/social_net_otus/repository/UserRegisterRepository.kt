package ru.kolesnikov.social_net_otus.repository

import org.springframework.data.repository.CrudRepository
import ru.kolesnikov.social_net_otus.entity.UserRegisterEntity
import java.util.*

interface UserRegisterRepository : CrudRepository<UserRegisterEntity, UUID?>