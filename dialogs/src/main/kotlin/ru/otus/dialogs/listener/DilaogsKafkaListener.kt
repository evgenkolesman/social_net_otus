package ru.otus.dialogs.listener

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import ru.otus.dialogs.service.SnoDialogManager
import ru.otus.dialogs.service.event.SnoDilaogEvent

const val DIALOGS_KAFKA_TOPIC = "DIALOGS_KAFKA_TOPIC"
const val DIALOGS_KAFKA_TOPIC_REQUEST = "DIALOGS_KAFKA_TOPIC_REQUEST"


@Service
class DialogsKafkaListener(
    private val snoDialogManager: SnoDialogManager
) {

    @KafkaListener(topics = [DIALOGS_KAFKA_TOPIC],
        containerFactory = "dialogsKafkaListenerContainerFactory")
        fun dailogsChange( event: SnoDilaogEvent) {
            snoDialogManager.manage(event)
        }

}