package com.example.cleanarch.core.utils

import android.content.res.Resources
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

class JsonResourceReader(val resources: Resources) {

    private fun readJson(id: Int): String {
        val resourceReader = resources.openRawResource(id)
        val reader = BufferedReader(InputStreamReader(resourceReader, "UTF-8"))
        var line: String? = reader.readLine()
        val writer = StringWriter()
        while (line != null) {
            writer.write(line)
            line = reader.readLine()
        }
        return writer.toString()
    }

    /**
     * Build an object from the specified JSON resource using Gson.
     *
     * @param type The type of the object to build.
     * @return An object of type T, with member fields populated using Gson.
     */
    fun <T> constructUsingGson(type: Class<T>, id: Int): T {
        val jsonString = readJson(id)
        val gson = GsonBuilder().create()
        return gson.fromJson(jsonString, type)
    }

    companion object {
        private val LOGTAG = JsonResourceReader::class.java.simpleName
    }

}
