package com.demo.services

import com.demo.repository.RedisRepository
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class UrlShortenerService(
    var hashInterface: B62Hash,
    var redisRepository: RedisRepository
) {

    @Value("\${app.short-host}")
    private lateinit var shortHost: String

    fun shortenUrl(url: String) : String {
        val urlIdentifier = hashInterface.calculateHash(url).take(5)
        redisRepository.put(urlIdentifier, url)
        val shortenedUrl = shortHost + urlIdentifier
        LOG.info("UrlShortenerService.shortenUrl $url -> $shortenedUrl")
        return shortenedUrl
    }

    fun expandUrl(shortenedUrl: String): String {
        val urlIdentifier = shortenedUrl.removePrefix(shortHost)
        LOG.info("UrlShortenerService.expandUrl $shortenedUrl -> $urlIdentifier")
        return redisRepository.get(urlIdentifier)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UrlShortenerService::class.java)
    }
}