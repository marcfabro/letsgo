package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places

import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.CommonConstants.KEY
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.CommonConstants.LANGUAGE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.KEYWORD
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.LOCATION
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.MAX_PRICE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.MIN_PRICE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.OPEN_NOW
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.PAGE_TOKEN
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.RADIUS
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.RANK_BY
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.TYPE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.QUERY
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.RankBy.DISTANCE
/**
 * Created by felixproehl on 07.05.17.
 */
/**
 * Different kinds of web requests with different result.
 * Each [RequestType] has its own request parameter rule set.
 *
 * @see <a href="https://developers.google.com/places/web-service/search">Place Search<a>
 */
enum class RequestType {
    /**
     * A Nearby Search lets you search for places within a specified area.
     * You can refine your search request by supplying keywords or specifying the type of place you are searching for.
     *
     * Request parameters:
     *
     * REQUIRED:
     *
     * [KEY],
     * [LOCATION],
     * [RADIUS]
     * (must not be included if: [DISTANCE] is set.
     *
     * OPTIONAL:
     *
     * [KEYWORD],
     * [LANGUAGE],
     * [MIN_PRICE],
     * [MAX_PRICE],
     * [OPEN_NOW],
     * [RANK_BY],
     * [TYPE],
     * [PAGE_TOKEN]
     *
     * @see <a href="https://developers.google.com/places/web-service/search#PlaceSearchRequests">Nearby Search<a>
     */
    NEAR_BY_SEARCH,

    /**
     * The Google Places API Text Search Service is a web service that returns information about a set of places based on a string.
     * The service responds with a list of places matching the text string and any location bias that has been set.
     *
     * IMPORTANT:
     * The Google Places search services share the same usage limits.
     * However, the Text Search service is subject to a 10-times multiplier.
     * That is, each Text Search request that you make will count as 10 requests against your quota.
     * If you've purchased the Google Places API as part of your Google Maps APIs Premium Plan contract, the multiplier may be different.
     * Please refer to the Google Maps APIs Premium Plan documentation for details.
     *
     * Request parameters:
     *
     * REQUIRED:
     *
     * [KEY],
     * [QUERY]
     * (optional, if [TYPE] parameter is also used in the search request)
     *
     * OPTIONAL:
     *
     * [LOCATION]
     * (if specified, [RADIUS] parameter must be specified, too),
     * [RADIUS],
     * [LANGUAGE],
     * [MIN_PRICE],
     * [MAX_PRICE],
     * [OPEN_NOW],
     * [TYPE],
     * [PAGE_TOKEN]
     *
     * @see <a href="https://developers.google.com/places/web-service/search#TextSearchRequests">Text Search<a>
     */
    TEXT_SEARCH,

    /**
     * The Google Places API Radar Search Service allows you to search for up to 200 places at once,
     * but with less detail than is typically returned from a Text Search or Nearby Search request.
     * With Radar Search, you can create applications that help users identify specific areas of interest within a geographic area.
     *
     * Request parameters:
     *
     * Must include atleast one of [KEYWORD],
     * or [TYPE]
     *
     * REQUIRED:
     *
     * [KEY],
     * [LOCATION],
     * [RADIUS]
     *
     * OPTIONAL:
     *
     * [KEYWORD],
     * [LANGUAGE],
     * [MIN_PRICE],
     * [MAX_PRICE],
     * [OPEN_NOW],
     * [TYPE]
     *
     * @see <a href="https://developers.google.com/places/web-service/search#RadarSearchRequests">Radar Search<a>
     */
    RADAR_SEARCH;

    override fun toString(): String {
        return super.toString().toLowerCase().filterNot { it in "_" }
    }
}