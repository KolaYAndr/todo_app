package com.cleverpumpkin.about.presentation

import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

private const val BUFFER_SIZE = 2048

class AssetReader {
    fun read(inputStream: InputStream): JSONObject {
        val data = getString(inputStream)
        return JSONObject(data)
    }

    private fun getString(inputStream: InputStream): String {
        val buffer = CharArray(BUFFER_SIZE)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val builder: StringBuilder = StringBuilder(inputStream.available())
        var read: Int
        while ((reader.read(buffer).also { read = it }) != -1) {
            builder.appendRange(buffer, 0, read)
        }
        return builder.toString()
    }
}
