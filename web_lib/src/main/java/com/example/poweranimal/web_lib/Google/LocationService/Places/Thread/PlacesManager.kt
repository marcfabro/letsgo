package com.example.poweranimal.web_lib.Google.LocationService.Places.Thread

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.util.LruCache
import com.example.poweranimal.web_lib.Google.LocationService.Places.Model.GooglePlaces
import com.example.poweranimal.web_lib.Google.LocationService.Places.PlacesHolder
import java.net.URL
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by felixproehl on 03.05.17.
 */

/**
 * Manages the web request download background thread [PlacesDownloadRunnable]
 * and the decoding background thread of the received file [PlacesDecodeRunnable] though its helper class [PlacesTask].
 *
 * Optionally, the received [java.io.InputStream] can be cached as [ByteArray].
 *
 * The input and the result [GooglePlaces] are handled over the [PlacesHolder].
 */
object PlacesManager {

    internal const val DOWNLOAD_FAILED: Int = -1

    internal const val DOWNLOAD_STARTED = 1

    internal const val DOWNLOAD_COMPLETE = 2

    internal const val DECODE_STARTED = 3

    internal const val TASK_COMPLETE = 4

    private const val PLACES_CACHE_SIZE = 1024 * 1024 * 4

    private const val KEEP_ALIVE_TIME = 1L

    private const val CORE_POOL_SIZE = 8

    private const val MAXIMUM_POOL_SIZE = 8

    private val KEEP_ALIVE_TIME_UNIT: TimeUnit = TimeUnit.SECONDS

    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()


    private val mPlacesCache: LruCache<URL, ByteArray>

    private val mDownloadWorkQueue: LinkedBlockingQueue<Runnable>

    private val mDecodeWorkQueue: LinkedBlockingQueue<Runnable>

    private val mTaskWorkQueue: Queue<PlacesTask>

    private val mDownloadThreadPool: ThreadPoolExecutor

    private val mDecodeThreadPool: ThreadPoolExecutor

    private val mHandler: Handler

    init {

        mDownloadWorkQueue = LinkedBlockingQueue<Runnable>()

        mDecodeWorkQueue = LinkedBlockingQueue<Runnable>()

        mTaskWorkQueue = LinkedBlockingQueue<PlacesTask>()

        mDownloadThreadPool = ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDownloadWorkQueue)

        mDecodeThreadPool = ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDecodeWorkQueue)

        mPlacesCache = object : LruCache<URL, ByteArray>(PLACES_CACHE_SIZE) {

            override fun sizeOf(paramURL: URL?, paramArrayOfByte: ByteArray?): Int {

                return paramArrayOfByte!!.size
            }
        }

        mHandler = object : Handler(Looper.getMainLooper()) {

            override fun handleMessage(msg: Message) {

                val placesTask = msg.obj as PlacesTask

                val placesHolder = placesTask.getPlacesHolder()

                if (placesHolder != null) {

                    val localURL = placesHolder.placesUrl


                    if (placesTask.placesURL === localURL) {


                        when (msg.what) {

                            DOWNLOAD_STARTED -> placesHolder.status = PlacesHolder.DOWNLOADING

                            DOWNLOAD_COMPLETE -> placesHolder.status = PlacesHolder.DECODING_QUEUED

                            DECODE_STARTED -> placesHolder.status = PlacesHolder.DECODING

                            TASK_COMPLETE -> {
                                placesHolder.googlePlaces = placesTask.googlePlaces
                                placesHolder.status = PlacesHolder.FINISHED
                                recycleTask(placesTask)
                            }

                            DOWNLOAD_FAILED -> {
                                placesHolder.status = PlacesHolder.DOWNLOAD_FAILED
                                recycleTask(placesTask)
                            }

                            else -> super.handleMessage(msg)
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles the state of the [PlacesTask].
     * <br></br>
     * Successful states will bring the result of the [PlacesTask] to the main thread.
     */
    fun handleState(placesTask: PlacesTask, state: Int) {

        when (state) {

            TASK_COMPLETE -> {

                if (placesTask.cacheEnabled) {
                    // If the task is set to cache the results, put the buffer
                    // that was
                    // successfully decoded into the cache
                    mPlacesCache.put(placesTask.placesURL, placesTask.byteBuffer)
                }

                val completeMessage = mHandler.obtainMessage(state, placesTask)

                completeMessage.sendToTarget()
            }

            DOWNLOAD_COMPLETE -> mDecodeThreadPool.execute(placesTask.decodeRunnable)

            else -> mHandler.obtainMessage(state, placesTask).sendToTarget()
        }
    }

    /**
     * Cancels all [PlacesDownloadRunnable]s threads that are currently executed.
     */
    fun cancelAll() {

        val tasks = mDownloadWorkQueue.filterIsInstance<PlacesDownloadRunnable>()

        synchronized(this, {
            tasks.forEach { it.mPlacesTask.currentThread?.interrupt() }
        })
    }

    /**
     *
     * Starts the task to get the requested place as [GooglePlaces]. All required information are contained in [PlacesHolder]
     * Procedure:
     *
     *  1. Checks if a [PlacesTask] had been recycled recently and can be reused [URL].
     *
     *  2. If no recycled [PlacesTask] is available, a new [PlacesTask] is being initialized.
     *
     *  3. The [PlacesTask] will then be initialized with the necessary information contained in the [PlacesHolder].
     *  The determination, if the [PlacesTask] shall be cached, will be done in this step, too.
     *
     *  4. The [ByteArray] in the [PlacesTask] is set based on the [ByteArray] that is
     * cached in the [.mPlacesCache]. Its retrieved by the [URL] that is set in
     * the [PlacesTask].
     *
     *  5. If a [ByteArray] with its corresponding [URL] had been cached, the [PlacesTask] executes its [PlacesDecodeRunnable] in order to decode the [Byte][] and
     * returns the [GooglePlaces] result to the [PlacesHolder].
     * If no [ByteArray] had been cached, the [PlacesTask] does the following:
     *
     *  * Starts the [PlacesDownloadRunnable].
     *
     *  * After successful completion, the [PlacesDecodeRunnable] will be started.
     *
     *  * The [GooglePlaces] result will be returned to the [PlacesHolder].
     *
     * @param placesHolder holds the input parameters and receives the [GooglePlaces] result.
     *
     * @param cacheFlag If set to true, the received [java.io.InputStream] from [PlacesDownloadRunnable] will be cached as [ByteArray]. Its key is the [PlacesTask]'s [URL].
     *
     * @return A reference to the recycled or newly created [PlacesTask] that is responsible for the executions.
     */
    fun startDownload(
            placesHolder: PlacesHolder,
            cacheFlag: Boolean): PlacesTask {

        var placesTask: PlacesTask? = mTaskWorkQueue.poll()

        if (placesTask == null) {
            placesTask = PlacesTask()
        }

        placesTask.initializeDownloaderTask(this, placesHolder, cacheFlag)

        placesTask.byteBuffer = mPlacesCache.get(placesTask.placesURL)

        if (placesTask.byteBuffer == null) {

            mDownloadThreadPool.execute(placesTask.downloadRunnable)

            // The place was cached, so no download is required.
        } else {
            handleState(placesTask, DOWNLOAD_COMPLETE)
        }

        return placesTask
    }

    /**
     * Removes a specific [PlacesTask]s [PlacesDownloadRunnable] that is being executed right now or is being queued.
     *
     * The [PlacesDecodeRunnable] will NOT be removed.
     *
     * @param placesTask The specific [PlacesTask] that holds the [PlacesDownloadRunnable] that shall be removed.
     * @param placesUrl The key that is required to remove the [PlacesDownloadRunnable].
     *                  Only if the given [URL] equals the [URL] in the [PlacesTask],
     *                  the [PlacesDownloadRunnable] will be removed.
     */
    fun removeDowload(
            placesTask: PlacesTask?,
            placesUrl: URL) {

        if (placesTask != null && placesTask.placesURL == placesUrl) {

            synchronized(this) {

                val thread = placesTask.currentThread

                thread?.interrupt()
            }

            mDownloadThreadPool.remove(placesTask.downloadRunnable)
        }
    }

    internal fun recycleTask(placesTask: PlacesTask) {

        // Frees up memory in the task
        placesTask.recycle()

        // Puts the task object back into the queue for re-use.
        mTaskWorkQueue.offer(placesTask)
    }
}