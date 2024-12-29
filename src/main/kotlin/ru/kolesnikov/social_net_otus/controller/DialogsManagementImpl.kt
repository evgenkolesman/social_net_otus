package ru.kolesnikov.social_net_otus.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.kolesnikov.social_net_otus.model.DialogMessage
import ru.kolesnikov.social_net_otus.model.DialogUserIdSendPostRequest
import ru.kolesnikov.social_net_otus.service.DialogsManagementService

@RestController
class DialogsManagementImpl(private val service: DialogsManagementService) : DialogsManagementApi {

    override fun dialogUserIdListGet(userId: String):
            ResponseEntity<List<DialogMessage>> = ResponseEntity.ok(service.getDialogsByUserId(userId))

    override fun dialogUserIdSendPost(
        userId: String,
        dialogUserIdSendPostRequest: DialogUserIdSendPostRequest?
    ): ResponseEntity<Unit> = ResponseEntity.ok(service.addDialogsByUserId(userId, dialogUserIdSendPostRequest!!))


}
