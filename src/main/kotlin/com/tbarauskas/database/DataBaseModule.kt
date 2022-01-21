package com.tbarauskas.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.koin.core.module.Module
import org.koin.dsl.module

fun postgresqlDbModule(postgresConfig: PostgresqlDataBaseConfig): Module {
    return module {
        single {
            val hikariConfig = HikariConfig()
            hikariConfig.jdbcUrl = "jdbc:${postgresConfig.dataBaseManager}://${postgresConfig.host}:${postgresConfig.port}/${postgresConfig.dataBaseName}"
            hikariConfig.username = postgresConfig.username
            hikariConfig.password = postgresConfig.password

            HikariDataSource(hikariConfig)
        }

        single {
            val dataSource = get<HikariDataSource>()
            val jdbi = Jdbi.create(dataSource)
            jdbi.installPlugin(SqlObjectPlugin())
            jdbi.installPlugin(KotlinPlugin())
            jdbi.installPlugin(KotlinSqlObjectPlugin())

            jdbi
        }
    }
}
