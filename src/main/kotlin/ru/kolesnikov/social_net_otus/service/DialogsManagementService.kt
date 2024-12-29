package ru.kolesnikov.social_net_otus.service

import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.model.DialogMessage
import ru.kolesnikov.social_net_otus.model.DialogUserIdSendPostRequest

@Service
class DialogsManagementService {
    fun dialogUserId(userId: String): List<DialogMessage> {
        TODO("Not yet implemented")
        return listOf()
    }

    fun dialogUserId(userId: String, dialogUserIdSendPostRequest: DialogUserIdSendPostRequest) {
        TODO("Not yet implemented")
    }
}
