package ru.kolesnikov.social_net_otus.service

import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.model.DialogMessage
import ru.kolesnikov.social_net_otus.model.DialogUserIdSendPostRequest
import ru.kolesnikov.social_net_otus.repository.DialogsManagementRepository

@Service
class DialogsManagementService(
    private val currentLoginProvider: CurrentLoginProvider,
    private val dialogsManagementRepository: DialogsManagementRepository
) {
    fun getDialogsByUserId(userId: String): List<DialogMessage> {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        val dialogMessages = dialogsManagementRepository.getDialogMessages(currentLogin, userId)
        return dialogMessages.map { DialogMessage(it.from, it.to, it.text) }
    }

    fun addDialogsByUserId(userId: String, postRequest: DialogUserIdSendPostRequest) {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        dialogsManagementRepository.addDialogMessages(currentLogin, userId, postRequest.text)
    }
}
