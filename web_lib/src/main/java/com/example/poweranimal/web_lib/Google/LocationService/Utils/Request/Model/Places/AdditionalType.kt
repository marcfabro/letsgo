package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places

/**
 * Created by felixproehl on 06.05.17.
 */

/**
 * ONLY return value in response.
 * Cannot be specified in request!
 *
 * @see <a href="https://developers.google.com/places/web-service/supported_types">Supported Types<a>
 */
enum class AdditionalType {

    ADMINISTRATIVE_AREA_LEVEL_1,
    ADMINISTRATIVE_AREA_LEVEL_2,
    ADMINISTRATIVE_AREA_LEVEL_3,
    ADMINISTRATIVE_AREA_LEVEL_4,
    ADMINISTRATIVE_AREA_LEVEL_5,
    COLLOQUIAL_AREA,
    COUNTRY,
    ESTABLISHMENT,
    FINANCE,
    FLOOR,
    FOOD,
    GENERAL_CONTRACTOR,
    GEOCODE,
    HEALTH,
    INTERSECTION,
    LOCALITY,
    NATURAL_FEATURE,
    NEIGHBORHOOD,
    PLACE_OF_WORSHIP,
    POLITICAL,
    POINT_OF_INTEREST,
    POST_BOX,
    POSTAL_CODE,
    POSTAL_CODE_PREFIX,
    POSTAL_CODE_SUFFIX,
    POSTAL_TOWN,
    PREMISE,
    ROOM,
    ROUTE,
    STREET_ADDRESS,
    STREET_NUMBER,
    SUBLOCALITY,
    SUBLOCALITY_LEVEL_4,
    SUBLOCALITY_LEVEL_5,
    SUBLOCALITY_LEVEL_3,
    SUBLOCALITY_LEVEL_2,
    SUBLOCALITY_LEVEL_1,
    SUBPREMISE;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}