package com.example.denet.data.utils

import java.security.MessageDigest

fun generateNodeName(parentName: String?): String {
    val randomString = generateRandomString()
    val initialData = randomString + parentName
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(initialData.toByteArray())

    val last20Bytes = hashBytes.takeLast(20).toByteArray()
    val nodeNameHash = last20Bytes.joinToString("") { byte ->
        String.format("%02x", byte)
    }

    return nodeNameHash
}

fun generateRandomString(): String {
    val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9') + listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')')
    val length = (10..20).random()
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
