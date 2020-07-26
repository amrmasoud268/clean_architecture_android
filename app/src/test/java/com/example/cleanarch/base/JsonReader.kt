package com.example.cleanarch.base

import com.google.gson.GsonBuilder
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object JsonReader {
    private fun readJson(fileName: String): String {
        return Files.lines(
            Paths.get(
                ClassLoader.getSystemClassLoader().getResource(fileName).toURI()
            )
        )
            .parallel().collect(Collectors.joining())
    }

    fun <T> constructUsingGson(type: Class<T>, fileName: String): T {
        return GsonBuilder().create().fromJson(readJson(fileName), type)
    }
}