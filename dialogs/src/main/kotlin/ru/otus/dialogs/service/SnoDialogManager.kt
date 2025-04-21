package ru.otus.dialogs.service

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.otus.dialogs.listener.DIALOGS_KAFKA_TOPIC_RESPONSE
import ru.otus.dialogs.repository.SnoDialogRepository
import ru.otus.dialogs.repository.SnoDialogEntity
import ru.otus.dialogs.service.event.OperationEnum
import ru.otus.dialogs.service.event.SnoDialogEvent
import java.time.Instant

@Service
@RequiredArgsConstructor
class SnoDialogManager
    (
    private val snoDialogRepository: SnoDialogRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {


    fun add(event: SnoDialogEvent) =
            snoDialogRepository.save(
                SnoDialogEntity(
                    null,
                    event.from,
                    event.to,
                    event.text,
                    Instant.now()
                )
            )


         fun manage(event: SnoDialogEvent) : Any =
                when (event.operation) {
                    OperationEnum.ADD.name -> snoDialogRepository.save(
                        SnoDialogEntity(
                            null,
                            event.from,
                            event.to,
                            event.text,
                            Instant.now()
                        )
                    )

                    OperationEnum.GET.name -> {
                        val dialogs = snoDialogRepository.findSnoDialogEntitiesByFromAndTo(event.from, event.to)

                        val transform: (SnoDialogEntity) -> SnoDialogEvent = { dialog ->
                            SnoDialogEvent(
                                dialog.id,
                                dialog.from,
                                dialog.to,
                                dialog.text,
                                dialog.timeModified,
                                OperationEnum.REQUEST.name
                            )
                        }
                        kafkaTemplate.send(DIALOGS_KAFKA_TOPIC_RESPONSE,
                            objectMapper.writeValueAsString(dialogs.map(transform)))
                    }

                    else -> { throw NotImplementedError() }
                }


}