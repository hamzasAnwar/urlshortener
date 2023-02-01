package com.demo.configs

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("redis")
class RedisConfig {
    var uri: String? = "localhost"
    var password: String? = null
    var expiry: Long? = 0L
}