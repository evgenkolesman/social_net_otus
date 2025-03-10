package ru.otus.dialogs.service

import lombok.RequiredArgsConstructor
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.otus.dialogs.listener.DIALOGS_KAFKA_TOPIC
import ru.otus.dialogs.listener.DIALOGS_KAFKA_TOPIC_REQUEST
import ru.otus.dialogs.repository.SnoDialogRepository
import ru.otus.dialogs.repository.SnoDilaogEntity
import ru.otus.dialogs.service.event.OperationEnum
import ru.otus.dialogs.service.event.SnoDilaogEvent
import java.time.Instant
import java.util.UUID

@Service
@RequiredArgsConstructor
class SnoDialogManager
    (
    private val snoDialogRepository: SnoDialogRepository,
    private val kafkaTemplate: KafkaTemplate<String, SnoDilaogEvent>,
) {

    fun manage(event: SnoDilaogEvent) : Any =
        when (event.operation) {
            OperationEnum.ADD.name -> snoDialogRepository.save(
                SnoDilaogEntity(
                    null,
                    event.from,
                    event.to,
                    event.text,
                    Instant.now()
                )
            )

            OperationEnum.GET.name -> {
                val id = event.id ?: UUID.randomUUID()
                val dialogs = snoDialogRepository.findAllById(id)
//                TODO rethink

                val transform: (SnoDilaogEntity) -> SnoDilaogEvent = { dialog ->

                    SnoDilaogEvent(
                        dialog.id,
                        dialog.from,
                        dialog.to,
                        dialog.text,
                        dialog.timeModified,
                        OperationEnum.REQUEST.name
                    )
                }
                kafkaTemplate.send(
                    DIALOGS_KAFKA_TOPIC_REQUEST,
                    dialogs.map(transform)
                )

            }

            else -> { throw NotImplementedError() }
        }


}