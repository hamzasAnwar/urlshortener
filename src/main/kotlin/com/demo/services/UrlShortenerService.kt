package com.demo.services

import com.demo.repository.RedisRepositoryService
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class UrlShortenerService(
    var hashInterface: B64HashService,
    var redisRepositoryService: RedisRepositoryService
) {

    @Value("\${app.short-host}")
    private lateinit var shortHost: String

    fun shortenUrl(url: String) : String {
        val urlIdentifier = hashInterface.calculateHash(url).takeLast(7)
        redisRepositoryService.put(urlIdentifier, url)
        val shortenedUrl = shortHost + urlIdentifier
        LOG.info("UrlShortenerService.shortenUrl $url -> $shortenedUrl")
        return shortenedUrl
    }

    fun expandUrl(shortenedUrl: String): String {
        val urlIdentifier = shortenedUrl.removePrefix(shortHost)
        LOG.info("UrlShortenerService.expandUrl $shortenedUrl -> $urlIdentifier")
        val expandedUrl = redisRepositoryService.get(urlIdentifier)
        return expandedUrl
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UrlShortenerService::class.java)
    }
}