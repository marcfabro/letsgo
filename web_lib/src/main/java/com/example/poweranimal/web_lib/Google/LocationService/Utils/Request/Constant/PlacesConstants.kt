package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant

import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.Price
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.RankBy
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.RankBy.DISTANCE
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places.Type
import com.google.android.gms.maps.model.LatLng

/**
 * Created by felixproehl on 06.05.17.
 */
object PlacesConstants {

    /**
     * The latitude/longitude around which to retrieve place information.
     * This must be specified [LatLng].
     */
    const val LOCATION = "location"

    /**
     * Defines the distance (in meters) within which to return place results.
     * The maximum allowed radius is 50 000 meters.
     * Note that [RADIUS] must not be included if [DISTANCE] is specified.
     */
    const val RADIUS = "radius"

    /**
     * A term to be matched against all content that Google has indexed for this place,
     * including but not limited to name, type, and address, as well as customer reviews and other third-party content.
     */
    const val KEYWORD = "keyword"

    /**
     * Restricts results to only those places within the specified range.
     * Valid values range between 0 (most affordable) to 4 (most expensive), inclusive.
     * The exact amount indicated by a specific value will vary from region to region.
     *
     * @see [Price]
     */
    const val MIN_PRICE = "minprice"

    /**
     * @see MIN_PRICE
     */
    const val MAX_PRICE = "maxprice"

    /**
     * Returns only those places that are open for business at the time the query is sent.
     * Places that do not specify opening hours in the Google Places database will not be returned
     * if you include this parameter in your query.
     */
    const val OPEN_NOW = "opennow"

    /**
     * Specifies the order in which results are listed.
     * Note that [RANK_BY] must not be included if [RADIUS] is specified.
     *
     * @see [RankBy]
     */
    const val RANK_BY = "rankby"

    /**
     * Restricts the results to places matching the specified type.
     * Only one type may be specified (if more than one type is provided,
     * all types following the first entry are ignored).
     *
     * @see [Type]
     */
    const val TYPE = "type"

    /**
     * Returns the next 20 results from a previously run search.
     * Setting a pagetoken parameter will execute a search with the same parameters used previously
     * — all parameters other than pagetoken will be ignored.
     */
    const val PAGE_TOKEN = "pagetoken"

    //TODO: replace REQUEST with class
    /**
     * The Google Places service will return candidate matches based on this string
     * and order the results based on their perceived relevance.
     * This parameter becomes optional if the type parameter is also used in the search request.
     *
     * Only for REQUEST
     */
    const val QUERY = "query"

}