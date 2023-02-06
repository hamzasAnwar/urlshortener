package com.demo.helpers

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

@MicronautTest
class UrlHelperSpec extends Specification {

    void 'application works'() {
        expect:
        UrlHelperKt.isValidUrl(url) == isValid

        where:
        url                                 | isValid
        'https://fs200.com'                 | true
        'https://fs200.com?a=true'          | true
        'https://facebook.de/somepage.html' | true
        'http://fs200.com'                  | true
        'http:/fs200.com'                   | false
        ''                                  | false
        'htt://face.c'                      | false
    }

}
