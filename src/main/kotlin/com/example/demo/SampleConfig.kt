package com.example.demo

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jdbc.core.convert.DataAccessStrategy
import org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.core.convert.SqlGeneratorSource
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionManager
import javax.sql.DataSource


@Configuration
@EnableJdbcRepositories(
        jdbcOperationsRef = SampleMainConfig.NAMED_PARAM_JDBC_OPRS,
        basePackages = ["com.example.demo.dao.sample.repository.main"],
        transactionManagerRef = SampleMainConfig.TRANSACTION_MANAGER,
        dataAccessStrategyRef = SampleMainConfig.DATA_ACCESS_STRATEGY
)
class SampleMainConfig : AbstractJdbcConfiguration() {

    companion object {
        const val DATA_SOURCE = "sampleMainDataSource"
        const val NAMED_PARAM_JDBC_OPRS = "sampleMainNamedParameterJdbcOperations"
        const val TRANSACTION_MANAGER = "sampleMainTransactionManager"
        const val DATA_ACCESS_STRATEGY = "sampleMainDataAccessStrategy"
    }

    @Primary
    @Bean(DATA_SOURCE)
    @ConfigurationProperties(prefix = "sample.main.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Primary
    @Bean(NAMED_PARAM_JDBC_OPRS)
    fun namedParameterJdbcOperations(@Qualifier(DATA_SOURCE) dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Primary
    @Bean(TRANSACTION_MANAGER)
    fun transactionManager(@Qualifier(DATA_SOURCE) dataSource: DataSource): TransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Primary
    @Bean(DATA_ACCESS_STRATEGY)
    override fun dataAccessStrategyBean(
            @Qualifier(NAMED_PARAM_JDBC_OPRS) operations: NamedParameterJdbcOperations,
            jdbcConverter: JdbcConverter,
            context: JdbcMappingContext,
            dialect: Dialect
    ): DataAccessStrategy {
        return DefaultDataAccessStrategy(
                SqlGeneratorSource(context, jdbcConverter, dialect),
                context, jdbcConverter, operations)
    }

}

@Configuration
@EnableJdbcRepositories(
        jdbcOperationsRef = SampleReplicaConfig.NAMED_PARAM_JDBC_OPRS,
        basePackages = ["com.example.demo.dao.sample.repository.replica"],
        transactionManagerRef = SampleReplicaConfig.TRANSACTION_MANAGER,
        dataAccessStrategyRef = SampleReplicaConfig.DATA_ACCESS_STRATEGY
)
class SampleReplicaConfig : AbstractJdbcConfiguration() {

    companion object {
        const val DATA_SOURCE = "sampleReplicaDataSource"
        const val NAMED_PARAM_JDBC_OPRS = "sampleReplicaNamedParameterJdbcOperations"
        const val TRANSACTION_MANAGER = "sampleReplicaTransactionManager"
        const val DATA_ACCESS_STRATEGY = "sampleReplicaDataAccessStrategy"
    }

    @Bean(DATA_SOURCE)
    @ConfigurationProperties(prefix = "sample.replica.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Bean(NAMED_PARAM_JDBC_OPRS)
    fun namedParameterJdbcOperations(@Qualifier(DATA_SOURCE) dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean(TRANSACTION_MANAGER)
    fun transactionManager(@Qualifier(DATA_SOURCE) dataSource: DataSource): TransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean(DATA_ACCESS_STRATEGY)
    override fun dataAccessStrategyBean(
            @Qualifier(NAMED_PARAM_JDBC_OPRS) operations: NamedParameterJdbcOperations,
            jdbcConverter: JdbcConverter,
            context: JdbcMappingContext,
            dialect: Dialect
    ): DataAccessStrategy {
        return DefaultDataAccessStrategy(
                SqlGeneratorSource(context, jdbcConverter, dialect),
                context, jdbcConverter, operations)
    }

}
