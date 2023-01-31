package com.demo.controllers

import com.demo.helpers.isValidUrl
import com.demo.services.UrlShortenerService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory

@Controller("/url-shortener")
class UrlShortenerController(
    private var urlShortenerService: UrlShortenerService,
){

    @Get(uri = "/", produces = [MediaType.TEXT_PLAIN])
    fun index() : String {
        LOG.info("UrlShortenerController.index is called")
        return "hello url-shortener world!"
    }

    @Post(uri = "/shorten", produces = [MediaType.APPLICATION_JSON])
    fun shorten(@Body body: Map<String, String>) : HttpResponse<Any> {
        if (!body.containsKey("url") || body.get("url").isNullOrEmpty()) {
            LOG.warn("UrlShortenerController.shorten: `url` is mandatory!")
            return HttpResponse.status<Any>(HttpStatus.BAD_REQUEST).body("`url` is mandatory!")
        }
        val url = body.getValue("url")
        if (!url.isValidUrl()) {
            LOG.warn("UrlShortenerController.shorten: `$url` is not a valid url!")
            return HttpResponse.status<Any>(HttpStatus.BAD_REQUEST).body("`$url` is not a valid url!")
        }
        val response = urlShortenerService.shortenUrl(url)
        return HttpResponse.status<Any>(HttpStatus.OK).body(response)
    }

    @Post(uri = "/expand", produces = [MediaType.APPLICATION_JSON])
    fun expand(@Body body: Map<String, String>) : HttpResponse<Any> {
        if (!body.containsKey("url") || body.get("url").isNullOrEmpty()) {
            LOG.warn("UrlShortenerController.shorten: `url` is mandatory!")
            return HttpResponse.status<Any>(HttpStatus.BAD_REQUEST).body("`url` is mandatory!")
        }
        val url = body.getValue("url")
        val response = urlShortenerService.expandUrl(url)
        if (response.isEmpty()) {
            return HttpResponse.status<Any>(HttpStatus.NOT_FOUND).body("`$url` is not found!")
        }
        return HttpResponse.status<Any>(HttpStatus.OK).body(response)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UrlShortenerController::class.java)
    }
}