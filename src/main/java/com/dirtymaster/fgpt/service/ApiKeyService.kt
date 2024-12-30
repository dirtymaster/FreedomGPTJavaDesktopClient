package com.dirtymaster.fgpt.service

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object ApiKeyService {
    private const val fileName: String = "key.txt"

    @JvmStatic
    @get:Throws(IOException::class)
    val keyFromFile: String
        get() {
            ApiKeyService::class.java.classLoader.getResourceAsStream(fileName).use { inputStream ->
                if (inputStream != null) {
                    return String(inputStream.readAllBytes(), StandardCharsets.UTF_8)
                }
            }
            // Затем пробуем из директории с jar
            try {
                val jarPath = File(
                    ApiKeyService::class.java.protectionDomain
                        .codeSource
                        .location
                        .toURI()
                ).path
                val directory = File(jarPath).parent
                val keyPath = Paths.get(directory, fileName)
                if (Files.exists(keyPath)) {
                    return Files.readString(keyPath)
                }
            } catch (`_`: Exception) {
            }

            // Наконец, пробуем из текущей директории
            val currentPath = Paths.get(fileName)
            if (Files.exists(currentPath)) {
                return Files.readString(currentPath)
            }

            throw FileNotFoundException(String.format("File %s not found", fileName))
        }
}
