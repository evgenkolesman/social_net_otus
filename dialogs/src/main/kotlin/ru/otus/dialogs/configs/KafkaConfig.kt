package ru.otus.dialogs.configs

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class KafkaConfig {

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String? = null
    @Value(value = "\${spring.kafka.consumer.group-id}")
    private val groupId: String? = null

//    Event replace with Dilaog

    @Bean
    fun dialogsConsumerFactory(): ConsumerFactory< in String, in String> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress!!
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId!!
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.CLIENT_ID_CONFIG] = "client_id_DIALOGS_SERVICE"
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun dialogsKafkaListenerContainerFactory() : ConcurrentKafkaListenerContainerFactory<String, String>{
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = dialogsConsumerFactory()
        return factory
    }


}