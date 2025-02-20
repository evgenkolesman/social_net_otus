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
                "spring.datasource.replicas.url=" + container.jdbcUrl + "&currentSchema=\${db.schema}",
                "spring.datasource.replicas.username=" + container.username,
                "spring.datasource.replicas.password=" + container.password,

                "spring.datasource.master.url=" + container.jdbcUrl + "&currentSchema=\${db.schema}",
                "spring.datasource.master.username=" + container.username,
                "spring.datasource.master.password=" + container.password,

                "spring.datasource.hikari.maximum-pool-size=5"
            ).applyTo(applicationContext)
        }
    }
}
