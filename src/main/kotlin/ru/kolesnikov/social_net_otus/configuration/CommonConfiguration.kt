package ru.kolesnikov.social_net_otus.configuration

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class CommonConfiguration {
    @Bean
    fun namedParameterJdbcTemplate(dataSource: DataSource): NamedParameterJdbcTemplate =
        NamedParameterJdbcTemplate(dataSource)

    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager =
        DataSourceTransactionManager(dataSource)

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        val module = JavaTimeModule()
        objectMapper.registerKotlinModule()
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        objectMapper.enable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
        objectMapper.registerModule(module)
        objectMapper.findAndRegisterModules()
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        objectMapper.enable(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
        objectMapper.enable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        objectMapper.disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
        return objectMapper
    }
}



