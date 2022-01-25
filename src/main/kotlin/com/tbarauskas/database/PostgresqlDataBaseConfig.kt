package com.tbarauskas.database

import com.typesafe.config.Config

data class PostgresqlDataBaseConfig(
    val dataBaseManager: String,
    val dataBaseName: String,
    val username: String,
    override val host: String,
    override val port: String,
    override val password: String
) : CommonDataBaseConfig {

    companion object {
        fun fromConfig(config: Config): PostgresqlDataBaseConfig {
            val postgresqlBranch = config.getConfig("postgresqlDb")
            return PostgresqlDataBaseConfig(
                dataBaseManager = postgresqlBranch.getString("dataBaseManager"),
                host = postgresqlBranch.getString("host"),
                port = postgresqlBranch.getString("port"),
                dataBaseName = postgresqlBranch.getString("dataBaseName"),
                username = postgresqlBranch.getString("username"),
                password = postgresqlBranch.getString("password")
            )
        }
    }
}
