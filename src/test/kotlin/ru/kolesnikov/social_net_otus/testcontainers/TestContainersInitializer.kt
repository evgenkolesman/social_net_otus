package ru.kolesnikov.social_net_otus.testcontainers

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

object TestContainersInitializer {

    @JvmStatic
    var container: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:14.9")

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=" + container.jdbcUrl + "&currentSchema=\${db.schema}",
                "spring.datasource.username=" + container.username,
                "spring.datasource.password=" + container.password,
                "spring.datasource.hikari.maximum-pool-size=5"
            ).applyTo(applicationContext)
        }
    }
}
