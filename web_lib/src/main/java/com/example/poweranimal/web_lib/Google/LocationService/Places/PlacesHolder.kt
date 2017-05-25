package com.example.poweranimal.web_lib.Google.LocationService.Places

import com.example.poweranimal.web_lib.Google.LocationService.Places.Model.GooglePlaces
import com.example.poweranimal.web_lib.Google.LocationService.Places.Thread.PlacesManager
import com.example.poweranimal.web_lib.Google.LocationService.Places.Thread.PlacesTask
import java.net.URL

/**
 * Created by felixproehl on 04.05.17.
 */

/**
 * Contains all necessary information for the [PlacesManager] that will initialize a [PlacesTask],
 * responsible for retrieving the requested [GooglePlaces]. The requested [GooglePlaces]
 * will be returned to this class.
 *
 * The [PlacesManager] will keep the [PlacesHolder] up to date about the status codes
 * thrown by the [PlacesTask].
 */
class PlacesHolder {

    interface OnPlacesHolderListener {
        fun onReceived(googlePlaces: GooglePlaces?)
    }

    companion object {

        const val DOWNLOAD_FAILED = -1
        const val IDLE = 0
        const val DOWNLOADING = 1
        const val DECODING_QUEUED = 2
        const val DECODING = 3
        const val FINISHED = 4

    }

    /**
     * URL for the [PlacesTask] in order to request [GooglePlaces] from Google Maps Api.
     */
    var placesUrl: URL?

    /**
     * Status codes thrown by [PlacesTask] and returned by [PlacesManager].
     */
    var status: Int

    /**
     * The requested [GooglePlaces] from [PlacesTask] returned by [PlacesManager].
     */
    var googlePlaces: GooglePlaces?
        set(value) {
            onPlacesHolderListener?.onReceived(value)
            field = value
        }

    /**
     * Triggered if [googlePlaces] was set.
     */
    var onPlacesHolderListener: OnPlacesHolderListener? = null

    init {

        placesUrl = null

        status = IDLE

        googlePlaces = null
    }

    constructor()

    constructor(placeUrl: URL) {
        this.placesUrl = placeUrl
    }

    /**
     * IMPORTANT: sets the [onPlacesHolderListener] to null, too.
     */
    fun recycle() {

        placesUrl = null

        googlePlaces = null

        onPlacesHolderListener = null

        setDefaults()
    }

    private fun setDefaults() {
        status = IDLE
    }
}