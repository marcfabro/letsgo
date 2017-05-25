package com.example.poweranimal.web_lib.Google.LocationService.Places.Thread

import com.example.poweranimal.web_lib.Google.LocationService.Places.Model.GooglePlaces
import com.example.poweranimal.web_lib.Google.LocationService.Places.PlacesHolder
import java.lang.ref.WeakReference
import java.net.URL

/**
 * Created by felixproehl on 04.05.17.
 */

/**
 * It's responsible for managing the [downloadRunnable] and [decodeRunnable] background thread's state codes.
 */
class PlacesTask : PlacesDownloadRunnable.TaskRunnableDownloadMethods, PlacesDecodeRunnable.TaskRunnableDecodeMethods {

    override var placesURL: URL? = null

    override var byteBuffer: ByteArray? = null

    override var googlePlaces: GooglePlaces? = null

    override var currentThread: Thread? = null
        get() = synchronized(mPlacesManager, {return field})
        set(value) = synchronized(mPlacesManager, {field = value})

    var cacheEnabled: Boolean = false

    internal var downloadRunnable: Runnable
        private set

    internal var decodeRunnable: Runnable
        private set

    private var mPlacesHolderWeak: WeakReference<PlacesHolder>? = null

    private var mPlacesManager: PlacesManager = PlacesManager


    init {

        downloadRunnable = PlacesDownloadRunnable(this)
        decodeRunnable = PlacesDecodeRunnable(this)
    }

    override fun handleDecodeState(state: Int) {

        val outState: Int

        when (state) {

            PlacesDecodeRunnable.DECODE_STATE_COMPLETED -> outState = PlacesManager.TASK_COMPLETE

            PlacesDecodeRunnable.DECODE_STATE_FAILED -> outState = PlacesManager.DOWNLOAD_FAILED

            else -> outState = PlacesManager.DECODE_STARTED
        }

        // Passes the state to the ThreadPool object.
        handleState(outState)

    }

    override fun handleDownloadState(state: Int) {

        val outState: Int

        when (state) {

            PlacesDownloadRunnable.HTTP_STATE_COMPLETED -> outState = PlacesManager.DOWNLOAD_COMPLETE

            PlacesDownloadRunnable.HTTP_STATE_FAILED -> outState = PlacesManager.DOWNLOAD_FAILED

            else -> outState = PlacesManager.DOWNLOAD_STARTED
        }
        // Passes the state to the ThreadPool object.
        handleState(outState)

    }

    fun initializeDownloaderTask(placesManager: PlacesManager, placesHolder: PlacesHolder, cacheFlag: Boolean) {

        placesURL = placesHolder.placesUrl

        cacheEnabled = cacheFlag

        mPlacesManager = placesManager

        mPlacesHolderWeak = WeakReference<PlacesHolder>(placesHolder)

    }

    fun recycle() {

        mPlacesHolderWeak?.clear()

        mPlacesHolderWeak = null
        byteBuffer = null
        googlePlaces = null
    }

    fun handleState(state: Int) {
        mPlacesManager.handleState(this, state)
    }

    fun getPlacesHolder(): PlacesHolder? {
        return mPlacesHolderWeak?.get()
    }

}