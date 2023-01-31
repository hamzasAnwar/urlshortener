package com.demo.helpers

import org.apache.commons.validator.routines.UrlValidator

// verifies if the string value is a valid url
fun String.isValidUrl() : Boolean {
    return UrlValidator().isValid(this)
}