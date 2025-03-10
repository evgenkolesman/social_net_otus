package ru.otus.dialogs.configs

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import ru.otus.dialogs.repository.SnoDilaogEntity

@Configuration
@EnableKafka
class KafkaConfig {

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String? = null

//    Event replace with Dilaog

    fun dialogsConsumerFactory(): ConsumerFactory<String, SnoDilaogEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress!!
        props[ConsumerConfig.GROUP_ID_CONFIG] = "dialgs"

        return DefaultKafkaConsumerFactory<String, SnoDilaogEntity>(
            props, StringDeserializer(),
            ErrorHandlingDeserializer<SnoDilaogEntity>(JsonDeserializer<SnoDilaogEntity>(SnoDilaogEntity::class.java))
        )
    }

    @Bean
    fun dialogsKafkaListenerContainerFactory() : ConcurrentKafkaListenerContainerFactory<String, SnoDilaogEntity>{
        val factory = ConcurrentKafkaListenerContainerFactory<String, SnoDilaogEntity>()
        factory.consumerFactory = dialogsConsumerFactory()
        return factory
    }

}