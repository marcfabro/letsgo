package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places

/**
 * Created by felixproehl on 06.05.17.
 */
enum class Price {

    MOST_AFFORDABLE,
    AFFORDABLE,
    REASONABLE,
    EXPENSIVE,
    MOST_EXPENSIVE;

    override fun toString(): String {
        return ordinal.toString()
    }

}