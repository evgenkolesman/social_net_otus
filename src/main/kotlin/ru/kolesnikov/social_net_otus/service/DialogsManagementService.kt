package ru.kolesnikov.social_net_otus.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.model.DialogMessage
import ru.kolesnikov.social_net_otus.repository.DialogsManagementRepository

@Service
class DialogsManagementService(
    private val currentLoginProvider: CurrentLoginProvider,
    private val dialogsManagementRepository: DialogsManagementRepository
) {
    @Transactional(readOnly = true)
    @Cacheable("dialogs_managements")
    fun getDialogsByUserId(userId: String): List<DialogMessage> {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        val dialogMessages = dialogsManagementRepository.getDialogMessages(currentLogin, userId)
        return dialogMessages.map { DialogMessage(it.from, it.to, it.text) }
    }

    @Transactional
    fun addDialogsByUserId(userId: String, postRequest: String) {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        dialogsManagementRepository.addDialogMessages(currentLogin, userId, postRequest)
    }
}
