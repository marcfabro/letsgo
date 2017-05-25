package com.example.poweranimal.web_lib.Utils.Gson.Deserializer

import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

/**
 * Created by felixproehl on 19.04.17.
 */

class LatLngDeserializer : JsonDeserializer<LatLng> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LatLng {

        val mJsonObject = json.asJsonObject

        val lat = mJsonObject.get("lat").asDouble
        val lng = mJsonObject.get("lng").asDouble

        return LatLng(lat, lng)
    }
}
