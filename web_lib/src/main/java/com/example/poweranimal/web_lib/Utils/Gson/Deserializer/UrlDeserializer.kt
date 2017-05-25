package com.example.poweranimal.web_lib.Utils.Gson.Deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException

import java.lang.reflect.Type
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by felixproehl on 06.05.17.
 */
//TODO: solve error -> "Malformed URL string"
class UrlDeserializer : JsonDeserializer<URL> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): URL? {

        var url: URL? = null

        try {
            url = URL(json.asString)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        return url
    }
}
