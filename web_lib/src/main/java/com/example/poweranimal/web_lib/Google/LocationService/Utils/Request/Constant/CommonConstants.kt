package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant

import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Builder.PlacesRequestBuilder

/**
 * Created by felixproehl on 06.05.17.
 */

/**
 * All constants are based on Google's documentation.
 *
 *
 * Last review: April 29th 2017
 *
 * @see
 *
 * <a href="https://developers.google.com/places/web-service/search">Places Requests</a>
 */
object CommonConstants {

    /**
     * Your application's API key. This key identifies your application for purposes of quota management.
     */
    val KEY = "key"

    /**
     *  *  [PlacesRequestBuilder]
     *
     *      The language code, indicating in which language the results should be returned, if possible.
     *
     * @see <a href="https://developers.google.com/maps/faq.languagesupport">Language Support</a>
     */
    val LANGUAGE = "language"
}
