package com.demo.repository

import com.demo.configs.RedisConfig
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class RedisRepository(
    var redisConfig: RedisConfig
) {

    fun put(key : String, value : String) {
        val redisConnection = getRedisConnection()
        val syncCommands = redisConnection.sync()
        // expire after 30 days when unused
        syncCommands.setex(key, redisConfig.expiry ?: 0, value)
        redisConnection.close()
        LOG.info("RedisRepository.put[$key,$value]")
    }

    fun get(key : String) : String {
        val redisConnection = getRedisConnection()
        val syncCommands = redisConnection.sync()
        val value = syncCommands[key]
        if (!value.isNullOrEmpty()) {
            // renew for another 30 days if it is expanded
            syncCommands.setex(key, redisConfig.expiry ?: 0, value)
        }
        redisConnection.close()
        LOG.info("RedisRepository.get[$key]=$value")
        return value ?: ""
    }

    private fun getRedisConnection() : StatefulRedisConnection<String, String> {
        val redisClient = RedisClient.create("redis://${redisConfig.password}@${redisConfig.uri}")
        return redisClient.connect()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(RedisRepository::class.java)
    }
}