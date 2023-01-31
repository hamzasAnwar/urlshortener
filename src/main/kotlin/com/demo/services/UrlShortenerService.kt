package com.demo.services

import com.demo.repository.RedisRepository
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton

@Singleton
class UrlShortenerService(
    var hashInterface: B62Hash,
    var redisRepository: RedisRepository
) {

    @Value("\${app.short-host}")
    private lateinit var shortHost: String

    fun shortenUrl(url: String) : String {
        var urlIdentifier = hashInterface.calculateHash(url)
        redisRepository.put(urlIdentifier, url)
        return shortHost + urlIdentifier
    }

    fun expandUrl(shortenedUrl: String): String {
        var urlIdentifier = shortenedUrl.removePrefix(shortHost)
        return redisRepository.get(urlIdentifier)
    }
}