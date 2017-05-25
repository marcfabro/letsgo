package com.example.poweranimal.web_lib.Google.LocationService.Places.Thread

import com.example.poweranimal.web_lib.Google.LocationService.Places.Model.GooglePlaces
import com.example.poweranimal.web_lib.Utils.Gson.Deserializer.LatLngDeserializer
import com.example.poweranimal.web_lib.Utils.Gson.Deserializer.UrlDeserializer
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.net.URL

/**
 * Created by felixproehl on 04.05.17.
 */

/**
 * Decodes the downloaded [ByteArray] to [GooglePlaces].
 *
 * IMPORTANT: It expects the [ByteArray] to be a Json file.
 */
internal class PlacesDecodeRunnable constructor(downloadTask: TaskRunnableDecodeMethods): Runnable {

    internal interface TaskRunnableDecodeMethods {

        var byteBuffer: ByteArray?

        fun handleDecodeState(state: Int)

        var googlePlaces: GooglePlaces?

        var currentThread: Thread?
    }

    companion object {

        internal const val DECODE_STATE_FAILED = -1
        internal const val DECODE_STATE_STARTED = 0
        internal const val DECODE_STATE_COMPLETED = 1

    }

    val mPlacesTask: TaskRunnableDecodeMethods = downloadTask

    override fun run() {

        mPlacesTask.currentThread = Thread.currentThread()

        val placesBuffer: ByteArray? = mPlacesTask.byteBuffer

        var googlePlaces: GooglePlaces? = null

        val inputStream: ByteArrayInputStream = ByteArrayInputStream(placesBuffer)

        val gsonBuilder: GsonBuilder = GsonBuilder()

        gsonBuilder.registerTypeAdapter(LatLng::class.java, LatLngDeserializer())

        gsonBuilder.registerTypeAdapter(URL::class.java, UrlDeserializer())

        val gson: Gson = gsonBuilder.create()

        if (Thread.interrupted()) {
            return
        }

        try {

            mPlacesTask.handleDecodeState(DECODE_STATE_STARTED)

            googlePlaces = gson.fromJson(InputStreamReader(inputStream), GooglePlaces::class.java)

            // Catches exceptions if something tries to activate the
            // Thread incorrectly.
        } finally {

            if (googlePlaces == null) {

                mPlacesTask.handleDecodeState(DECODE_STATE_FAILED)

                Timber.e("Download failed in PlacesDecodeRunnable")

            } else {

                mPlacesTask.googlePlaces = googlePlaces

                mPlacesTask.handleDecodeState(DECODE_STATE_COMPLETED)
            }

            mPlacesTask.currentThread = null

            Thread.interrupted()
        }
    }
}