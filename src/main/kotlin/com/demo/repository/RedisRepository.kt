package com.demo.repository

import com.demo.configs.RedisConfig
import io.lettuce.core.RedisClient
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class RedisRepository(
    var redisConfig: RedisConfig
) {

    fun put(key : String, value : String) {
        val redisClient = RedisClient.create("redis://${redisConfig.password}@${redisConfig.uri}")
        val connection = redisClient.connect()
        val syncCommands = connection.sync()
        syncCommands[key] = value
        connection.close()
        LOG.info("RedisRepository.put[$key,$value]")
    }

    fun get(key : String) : String {
        val redisClient = RedisClient.create("redis://${redisConfig.password}@${redisConfig.uri}")
        val connection = redisClient.connect()
        val syncCommands = connection.sync()
        val value = syncCommands[key]
        connection.close()
        LOG.info("RedisRepository.get[$key]=$value")
        return value ?: ""
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(RedisRepository::class.java)
    }
}