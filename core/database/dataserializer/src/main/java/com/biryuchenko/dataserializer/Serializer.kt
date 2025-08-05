package com.biryuchenko.dataserializer

import android.content.Context
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException


class Serializer {

    private val FILE_NAME = "Category.json"

    fun write(context: Context, name: String, percent: Int) {
        val data = listOf<Category>(Category(name, percent))
        try {
            File(context.filesDir, FILE_NAME).writeText(Json.encodeToString(data))
        } catch (e: IOException) {

        } finally {

        }
    }

    fun read(context: Context): String {
        val content = File(context.filesDir, FILE_NAME).readText()
        return content
    }


}