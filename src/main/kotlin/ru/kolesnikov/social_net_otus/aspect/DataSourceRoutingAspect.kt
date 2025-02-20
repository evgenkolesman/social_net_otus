package ru.kolesnikov.social_net_otus.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.mapstruct.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronizationManager
import ru.kolesnikov.social_net_otus.configuration.MASTER
import ru.kolesnikov.social_net_otus.configuration.REPLICA
import ru.kolesnikov.social_net_otus.configuration.ReplicationRoutingDataSource
import javax.sql.DataSource

@Aspect
@Component
class DataSourceRoutingAspect(
    private val routingDataSource: DataSource
) {

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    fun routeDataSource(joinPoint: ProceedingJoinPoint): Any {
        val transactionReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
        routingDataSource as ReplicationRoutingDataSource
        // Выбор источника данных в зависимости от транзакции
        if (transactionReadOnly) {
            // Если транзакция для чтения, маршрутизируем на реплику
            routingDataSource.setCurrentDataSource(REPLICA)
        } else {
            // Если транзакция для записи, маршрутизируем на мастер
            routingDataSource.setCurrentDataSource(MASTER)
        }

        // Продолжить выполнение метода
        return joinPoint.proceed()
    }
}
