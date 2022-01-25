package com.tbarauskas.database.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import org.koin.core.module.Module
import org.koin.dsl.module

fun redisCachingModule(redisConfig: RedisConfig): Module {
    return module {
        single {
            val client: RedisClient =
                RedisClient.create("redis://${redisConfig.password}@${redisConfig.host}:${redisConfig.port}")
            val connection: StatefulRedisConnection<String, String> = client.connect()
            val sync: RedisCommands<String, String> = connection.sync()

            RedisCacheService(sync)
        }
    }
}
