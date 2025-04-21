package ru.otus.dialogs.listener

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import ru.otus.dialogs.service.SnoDialogManager
import ru.otus.dialogs.service.event.SnoDialogEvent

const val DIALOGS_KAFKA_TOPIC_RESPONSE = "DIALOGS_KAFKA_TOPIC_RESPONSE"
const val DIALOGS_KAFKA_TOPIC_REQUEST = "DIALOGS_KAFKA_TOPIC_REQUEST"


@Service
class DialogsKafkaListener(
    private val snoDialogManager: SnoDialogManager,
    val objectMapper: ObjectMapper

) {

    val log = LoggerFactory.getLogger(DialogsKafkaListener::class.java)

    @KafkaListener(
        topics = [DIALOGS_KAFKA_TOPIC_REQUEST], groupId = "group_1")
//        ,containerFactory = "dialogsKafkaListenerContainerFactory")
        fun dialogsChange(key:String, event: String) {
            log.info("Received event: $key")
            log.info("Received event: $event")
        val eventMapped = objectMapper.readValue(event, SnoDialogEvent::class.java)

        snoDialogManager.manage(eventMapped)
        }

}