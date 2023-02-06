package com.demo.services

import com.demo.interfaces.HashInterface
import jakarta.inject.Singleton
import java.util.Base64

@Singleton
class B64HashService : HashInterface {
    override fun calculateHash(url: String): String {
        return Base64.getEncoder().encodeToString(url.toByteArray())
    }
}