package com.demo

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class UrlshortnerSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application

    void 'application works'() {
        expect:
        application.running
    }

}
