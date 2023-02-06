package com.demo.services

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class B64HashServiceSpec extends Specification {

    @Inject
    B64HashService service

    def "calculateHash calculates hash correctly"() {
        expect:
        service.calculateHash(input) == output

        where:
        input                   | output
        'https://facebook.com'  | 'aHR0cHM6Ly9mYWNlYm9vay5jb20='
        'http://facebook.com'   | 'aHR0cDovL2ZhY2Vib29rLmNvbQ=='
    }
}
