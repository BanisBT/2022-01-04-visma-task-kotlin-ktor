package com.tbarauskas.database.redis

import io.lettuce.core.api.sync.RedisCommands

class RedisCacheService(
    private val redisSync: RedisCommands<String, String>
) {
    fun setDataToCache(entryKey: String, valuesAsString: String, timeToLiveSeconds: Long) {
        redisSync.setex(entryKey, timeToLiveSeconds, valuesAsString)
    }

    fun getDataFromCache(key: String): String? {
        return redisSync[key]
    }
}
