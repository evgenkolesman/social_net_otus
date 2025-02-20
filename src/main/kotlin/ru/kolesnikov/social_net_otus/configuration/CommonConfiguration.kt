package ru.kolesnikov.social_net_otus.configuration

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.atomic.AtomicInteger
import javax.sql.DataSource


const val MASTER = "MASTER"

const val REPLICA = "REPLICA"

@Configuration
class CommonConfiguration(private val replicaProperties: ReplicaDataSourcesProperties) {

    @Bean
    @FlywayDataSource
    fun flywayDataSource(masterDataSource: DataSource): DataSource = masterDataSource

    @Bean
    fun namedParameterJdbcTemplate(dataSource: DataSource): NamedParameterJdbcTemplate =
        NamedParameterJdbcTemplate(dataSource)

    @Bean
    fun transactionManager(routingDataSource: DataSource): PlatformTransactionManager =
        DataSourceTransactionManager(routingDataSource)

    @Bean("routingDataSource")
    @Primary
    fun routingDataSource(
        masterDataSource: DataSource,
        replicaDataSources: List<DataSource>  //list of slaves
    ): DataSource =
        ReplicationRoutingDataSource(
            mapOf(
                Pair(MASTER, listOf(masterDataSource)),
                Pair(REPLICA, replicaDataSources)
            )
        )


    @Bean
    fun masterDataSource(): DataSource {
        val ds = HikariDataSource()
        ds.jdbcUrl = replicaProperties.master?.url
        ds.username = replicaProperties.master?.username
        ds.password = replicaProperties.master?.password
        return ds
    }


    @Bean
    fun replicaDataSources(): List<DataSource> {
        return replicaProperties.replicas?.map { config ->
            val ds = HikariDataSource()
            ds.jdbcUrl = config.url
            ds.username = config.username
            ds.password = config.password
            ds
        }?.toList() as List<DataSource>
    }

}


@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
data class ReplicaDataSourcesProperties(
    var master: ReplicaConfig? = null,
    var replicas: List<ReplicaConfig>? = null,
)

class ReplicaConfig(
    val url: String,
    val username: String,
    val password: String,


    )

class ReplicationRoutingDataSource(
    private val replicas: Map<String, List<DataSource>>,
    private val counter: AtomicInteger = AtomicInteger()
) : AbstractRoutingDataSource() {
    override fun afterPropertiesSet() {
        super.setDefaultTargetDataSource(replicas[MASTER]!![0])
        super.setTargetDataSources(
            buildMap {
                replicas[MASTER]?.first().let { put(MASTER, it) }

                replicas[REPLICA]?.forEachIndexed { index, dataSource ->
                    put("$REPLICA-$index", dataSource)
                }
            }
        )
        super.afterPropertiesSet()
    }

    override fun determineCurrentLookupKey(): String {
        //        TODO need recheck
        return if (currentDataSourceKey == REPLICA) {
            val replicaDataSources = replicas[REPLICA]
            val index = counter.getAndIncrement() % replicaDataSources?.size!!
            "$REPLICA-$index"
        } else {
            MASTER
        }

    }


    fun setCurrentDataSource(key: String) {
        currentDataSourceKey = key
    }

    private var currentDataSourceKey = MASTER
}


