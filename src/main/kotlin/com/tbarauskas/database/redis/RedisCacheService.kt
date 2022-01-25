package com.tbarauskas.database.redis

import io.lettuce.core.api.sync.RedisCommands

class RedisCacheService(
    val redisSync: RedisCommands<String, String>
) {


}
