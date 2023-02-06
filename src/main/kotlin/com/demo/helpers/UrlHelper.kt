package com.demo.helpers

import org.apache.commons.validator.routines.UrlValidator

// verifies if the string value is a valid url
fun isValidUrl(url: String) : Boolean {
    return UrlValidator().isValid(url)
}