package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Builder

import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.CommonConstants.KEY
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.CommonConstants.LANGUAGE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.KEYWORD
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.LOCATION
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.MAX_PRICE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.MIN_PRICE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.OPEN_NOW
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.PAGE_TOKEN
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.QUERY
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.RADIUS
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.RANK_BY
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.TYPE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Common.Language
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Common.ResponseFormat
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Common.WebProtocol
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.Price
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.RankBy
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.RequestType
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.Type
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber
import java.net.URL


/**
 * Created by felixproehl on 06.05.17.
 */
/**
 * Http Request Builder for Google Maps Directions.
 *
 * IMPORTANT: Read Google's documentation about the parameters that must and must not be included in the request!
 *
 * @see <a href="https://developers.google.com/places/web-service/search">Google Place Search</a>
 */
class PlacesRequestBuilder constructor(val webRequest: String, var apiKey: String) {

    var protocol: WebProtocol = WebProtocol.HTTPS

    var requestType: RequestType = RequestType.NEAR_BY_SEARCH

    var responseFormat: ResponseFormat = ResponseFormat.JSON

    var language: Language? = null

    var location: LatLng? = null

    var radius: Int? = null

    var keyword: String? = null

    var minPrice: Price? = null

    var maxPrice: Price? = null

    var openNow: Boolean? = null

    var rankBy: RankBy? = null

    var type: Type? = null

    var pagetoken: String? = null

    var query: String? = null



    fun build(): URL {

        val builder: StringBuilder = StringBuilder()

        val file: String = builder

                .append(responseFormat.toString())
                .append("?")

                .appendUrl(LOCATION, location.takeIf { it != null }?.toReadableString())

                .appendUrl(LANGUAGE, language.takeIf { it != null }?.toString())

                .appendUrl(RADIUS, radius.takeIf { it != null }?.toString())

                .appendUrl(KEYWORD, keyword)

                .appendUrl(MIN_PRICE, minPrice.takeIf { it != null }?.toString())

                .appendUrl(MAX_PRICE, maxPrice.takeIf { it != null }?.toString())

                .appendUrl(OPEN_NOW, openNow.takeIf { it != null }?.toString())

                .appendUrl(RANK_BY, rankBy.takeIf { it != null }?.toString())

                .appendUrl(TYPE, type.takeIf { it != null }?.toString())

                .appendUrl(PAGE_TOKEN, pagetoken)

                .appendUrl(QUERY, query)

                .appendUrl(KEY, apiKey, { Timber.w("*** Invalid request***\nApiKey is empty") })

                .deleteCharAt(builder.lastIndex)

                .toString()

        return URL(protocol.toString(),
                buildHost(),
                file)
    }

    fun LatLng.toReadableString(): String {
        return this.latitude.toString() + "," + this.longitude.toString()
    }

    fun StringBuilder.appendUrl(identifier: String, value: String?): StringBuilder {

        if (value.isNullOrEmpty()) return this

        this
                .append(identifier)
                .append("=")
                .append(value)
                .append("&")

        return this
    }

    fun StringBuilder.appendUrl(identifier: String, value: String?, isNullOrEmpty: () -> Unit): StringBuilder {

        if (value.isNullOrEmpty()) {
            isNullOrEmpty.invoke()
            return this
        }

        this
                .append(identifier)
                .append("=")
                .append(value)
                .append("&")

        return this
    }

    fun recycle() {

        apiKey = ""

        language = null

        location = null

        radius = null

        keyword = null

        minPrice = null

        maxPrice = null

        openNow = null

        rankBy = null

        type = null

        pagetoken = null

        query = null

        setDefaults()
    }


    private fun setDefaults() {

        protocol = WebProtocol.HTTPS

        requestType = RequestType.NEAR_BY_SEARCH

        responseFormat = ResponseFormat.JSON

    }

    private fun buildHost(): String {
        return webRequest.apply {
            isEmpty().run { Timber.w("*** Invalid request***\nWebRequest is empty") }
        } + requestType + "/"
    }
}

