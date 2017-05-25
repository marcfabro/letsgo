package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Places

import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Constant.PlacesConstants.RADIUS
/**
 * Created by felixproehl on 07.05.17.
 */

/**
 * Must not be included if [RADIUS] is specified.
 */
enum class RankBy {

    /**
     * Sorts results based on their importance.
     * Ranking will favor prominent places within the specified area.
     * Prominence can be affected by a place's ranking in Google's index, global popularity, and other factors.
     */
    PROMINENCE,

    DISTANCE;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}