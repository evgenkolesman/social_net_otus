package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.service.FriendsManagementService

@RestController
class FriendsManagementImpl(
    private val service: FriendsManagementService,

    ) : FriendsManagementApi {
    override fun friendDeleteUserIdPut(userId: String):
            ResponseEntity<Unit> = ResponseEntity.ok(service.friendDeleteUserId(userId))

    override fun friendSetUserIdPut(userId: String):
            ResponseEntity<Unit> = ResponseEntity.ok(service.friendSetUserId(userId))


}
