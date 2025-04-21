package ru.kolesnikov.social_net_otus.configuration

import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer
import com.fasterxml.jackson.databind.ser.std.StringSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import ru.otus.dialogs.service.event.SnoDialogEvent


const val DIALOGS_KAFKA_TOPIC_RESPONSE = "DIALOGS_KAFKA_TOPIC_RESPONSE"
const val DIALOGS_KAFKA_TOPIC_REQUEST = "DIALOGS_KAFKA_TOPIC_REQUEST"

@Configuration
class KafkaProducerConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapService: String? = null

    @Value("\${spring.kafka.producer.request.timeout.ms}")
    private val requestTimeout: String? = null
    @Value(value = "\${spring.kafka.consumer.group-id}")
    private val groupId: String? = null

    fun producerConfig(): Map<String, Any?> {
        val props = HashMap<String, Any?>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapService
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonValueSerializer::class.java
        props[ProducerConfig.ACKS_CONFIG] = "all"
        props[ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG] = requestTimeout
        props[ProducerConfig.BATCH_SIZE_CONFIG] = 25
        //        props.put(ProducerConfig.CLIENT_ID_CONFIG, "client_id");
        return props
    }

//    @Bean
    fun kafkaProducerFactory(): ProducerFactory<String, SnoDialogEvent> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

//    @Bean
//    @Primary
    fun kafkaTemplate(producerFactory: ProducerFactory<String, SnoDialogEvent>): KafkaTemplate<String, SnoDialogEvent> {
        return KafkaTemplate(producerFactory)
    }

    @Bean
    fun dialogsConsumerFactory(): ConsumerFactory<in String, in SnoDialogEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapService!!
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId!!

        return DefaultKafkaConsumerFactory(
            props, StringDeserializer(),
            ErrorHandlingDeserializer(JsonDeserializer(SnoDialogEvent::class.java))
        )
    }

    @Bean
    fun dialogsKafkaListenerContainerFactory() : ConcurrentKafkaListenerContainerFactory<String, SnoDialogEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SnoDialogEvent>()
        factory.consumerFactory = dialogsConsumerFactory()
        return factory
    }
}