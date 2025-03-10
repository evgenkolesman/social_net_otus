package ru.kolesnikov.social_net_otus.configuration

import com.fasterxml.jackson.databind.ser.std.StringSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
@EnableKafka
class KafkaProducerConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapService: String? = null

    @Value("\${spring.kafka.producer.request.timeout.ms}")
    private val requestTimeout: String? = null

    fun producerConfig(): Map<String, Any?> {
        val props = HashMap<String, Any?>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapService
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.ACKS_CONFIG] = "all"
        props[ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG] = requestTimeout
        props[ProducerConfig.BATCH_SIZE_CONFIG] = 25
        //        props.put(ProducerConfig.CLIENT_ID_CONFIG, "client_id");
        return props
    }

    @Bean
    fun kafkaProducerFactory(): ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, String>): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory)
    }
}