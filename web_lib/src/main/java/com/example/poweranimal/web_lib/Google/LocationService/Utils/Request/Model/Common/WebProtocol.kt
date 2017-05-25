package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Common

/**
 * Created by felixproehl on 25.04.17.
 */

enum class WebProtocol {

    HTTP,
    HTTPS;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}
