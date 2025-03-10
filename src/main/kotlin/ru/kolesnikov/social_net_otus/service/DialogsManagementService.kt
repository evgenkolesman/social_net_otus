package ru.kolesnikov.social_net_otus.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.model.DialogMessage
import ru.kolesnikov.social_net_otus.repository.DialogsManagementRepository
import ru.otus.dialogs.service.event.OperationEnum
import ru.otus.dialogs.service.event.SnoDialogEvent

const val DIALOGS_KAFKA_TOPIC = "DIALOGS_KAFKA_TOPIC"


@Service
class DialogsManagementService(
    private val currentLoginProvider: CurrentLoginProvider,
    private val dialogsManagementRepository: DialogsManagementRepository,
    private val kafkaTemplate : KafkaTemplate<String, SnoDialogEvent>

) {


    @Cacheable("dialogs_managements")
    fun getDialogsByUserId(userId: String): List<DialogMessage> {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        val dialogMessages = dialogsManagementRepository.getDialogMessages(currentLogin, userId)
        return dialogMessages.map { DialogMessage(it.from, it.to, it.text) }
        //        TODO remake with Kafka

    }

    fun addDialogsByUserId(userId: String, postRequest: String) {
        val currentLogin = currentLoginProvider.getCurrentLogin()
//        TODO remake with Kafka
        kafkaTemplate.send(
            DIALOGS_KAFKA_TOPIC,
            SnoDialogEvent(
            null,
            currentLogin,
                userId,
                postRequest,
                null,
                OperationEnum.ADD.name
                )
        )
    }
}
