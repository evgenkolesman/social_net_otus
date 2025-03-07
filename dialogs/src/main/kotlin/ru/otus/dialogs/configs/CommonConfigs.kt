package ru.otus.dialogs.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableJdbcAuditing
class CommonConfigs {
    @Bean
    fun namedParameterJdbcTemplate(dataSource: DataSource): NamedParameterJdbcTemplate =
        NamedParameterJdbcTemplate(dataSource)

    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager =
        DataSourceTransactionManager(dataSource)

//    @Bean
//    fun jdbcMappingContext(): JdbcMappingContext {
//        val mappingContext = JdbcMappingContext(DefaultNamingStrategy())
//        mappingContext.isForceQuote = false
//        return mappingContext
//    }

    @Bean
    fun auditorAware(): AuditorAware<String> {
        // Implement to provide the current user id for auditing fields
        return AuditorAware { Optional.of("system") }
    }
}