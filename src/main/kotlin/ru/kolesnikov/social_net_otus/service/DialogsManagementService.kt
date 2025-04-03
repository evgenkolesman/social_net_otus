package ru.kolesnikov.social_net_otus.service

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.tomcat.util.collections.SynchronizedQueue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.kolesnikov.social_net_otus.configuration.CurrentLoginProvider
import ru.kolesnikov.social_net_otus.configuration.DIALOGS_KAFKA_TOPIC_REQUEST
import ru.kolesnikov.social_net_otus.configuration.DIALOGS_KAFKA_TOPIC_RESPONSE
import ru.kolesnikov.social_net_otus.model.DialogMessage
import ru.otus.dialogs.service.event.OperationEnum
import ru.otus.dialogs.service.event.SnoDialogEvent
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@Service
class DialogsManagementService(
    private val currentLoginProvider: CurrentLoginProvider,
    private val kafkaConsumer: KafkaConsumer,
    private val kafkaTemplate : KafkaTemplate<String, String>,
    val objectMapper: ObjectMapper

) {


    @Cacheable("dialogs_managements")
    fun getDialogsByUserId(userId: String): List<DialogMessage> {
        val currentLogin = currentLoginProvider.getCurrentLogin()
        kafkaTemplate.send(
            DIALOGS_KAFKA_TOPIC_REQUEST,
            userId + currentLogin,
            objectMapper.writeValueAsString(SnoDialogEvent(
                null,
                currentLogin,
                userId,
                "1",
                null,
                OperationEnum.GET.name
            ))
        )
        kafkaConsumer.countDownLatch.await(2000, TimeUnit.SECONDS)
        val dialogMessages : List<SnoDialogEvent> = kafkaConsumer.queue.poll() ?: listOf()
       return if (dialogMessages.isNotEmpty()) {
            return dialogMessages.map { DialogMessage(it.from, it.to, it.text) }
        } else listOf()

    }

    fun addDialogsByUserId(userId: String, postRequest: String) {
        val currentLogin = currentLoginProvider.getCurrentLogin()
//        TODO remake with Kafka
        kafkaTemplate.send(
            DIALOGS_KAFKA_TOPIC_REQUEST,
            objectMapper.writeValueAsString(SnoDialogEvent(
            null,
                currentLogin,
                userId,
                postRequest,
                null,
                OperationEnum.ADD.name
                ))
        )
    }
}

@Service
class KafkaConsumer(val objectMapper: ObjectMapper) {
    val queue = SynchronizedQueue<List<SnoDialogEvent>>()
    val logger: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)
    val countDownLatch = CountDownLatch(1)

    @KafkaListener(topics = [DIALOGS_KAFKA_TOPIC_RESPONSE],
        groupId = "group_1")
    fun getEvents(event: String) {

        logger.info("Received event: $event")
        val value = objectMapper.readValue(event, Array<SnoDialogEvent>::class.java).toList()

        queue.offer(value)
        countDownLatch.countDown()
    }

}