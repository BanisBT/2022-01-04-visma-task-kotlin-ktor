package com.tbarauskas.database.redis

import com.tbarauskas.database.CommonDataBaseConfig
import com.typesafe.config.Config

data class RedisConfig(
    override val host: String,
    override val port: String,
    override val password: String
): CommonDataBaseConfig {

    companion object {
        fun fromConfig(config: Config): RedisConfig {
            val redisBranch = config.getConfig("redis")
            return RedisConfig(
                host = redisBranch.getString("host"),
                port = redisBranch.getString("port"),
                password = redisBranch.getString("password")
            )
        }
    }
}
