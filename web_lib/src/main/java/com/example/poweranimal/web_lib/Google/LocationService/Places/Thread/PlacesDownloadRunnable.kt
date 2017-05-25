package com.example.poweranimal.web_lib.Google.LocationService.Places.Thread

import com.example.poweranimal.web_lib.Constants
import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.net.URL

/**
 * Created by felixproehl on 04.05.17.
 */
internal class PlacesDownloadRunnable constructor(placesTask: TaskRunnableDownloadMethods): Runnable {

    internal interface TaskRunnableDownloadMethods {

        var byteBuffer: ByteArray?

        fun handleDownloadState(state: Int)

        var placesURL: URL?

        var currentThread: Thread?

    }

    companion object {

        private const val READ_SIZE = 1024 * 2

        internal const val HTTP_STATE_FAILED = -1
        internal const val HTTP_STATE_STARTED = 0
        internal const val HTTP_STATE_COMPLETED = 1

    }

    val mPlacesTask: TaskRunnableDownloadMethods = placesTask

    override fun run() {

        mPlacesTask.currentThread = Thread.currentThread()

        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)

        var byteBuffer: ByteArray? = mPlacesTask.byteBuffer

        try {

            if (Thread.interrupted()) {
                throw InterruptedException()
            }

            if (byteBuffer == null) {

                mPlacesTask.handleDownloadState(HTTP_STATE_STARTED)

                var byteStream: InputStream? = null

                try {

                    val httpConn = mPlacesTask.placesURL?.openConnection()

                    httpConn?.setRequestProperty("User-Agent", Constants.USER_AGENT)

                    if (Thread.interrupted()) {
                        throw InterruptedException()
                    }

                    byteStream = httpConn?.inputStream

                    if (Thread.interrupted()) {
                        throw InterruptedException()
                    }

                    val contentSize = httpConn?.contentLength

                    if (contentSize == -1) {

                        // Allocates a temporary buffer
                        var tempBuffer = ByteArray(READ_SIZE)

                        // Records the initial amount of available space
                        var bufferLeft = tempBuffer.size

                        var bufferOffset = 0
                        var readResult = 0

                        outer@ do {

                            while (bufferLeft > 0) {

                                readResult = byteStream!!.read(tempBuffer, bufferOffset,
                                        bufferLeft)

                                if (readResult < 0) {
                                    break@outer
                                }

                                bufferOffset += readResult

                                bufferLeft -= readResult

                                if (Thread.interrupted()) {
                                    throw InterruptedException()
                                }
                            }

                            bufferLeft = READ_SIZE

                            val newSize = tempBuffer.size + READ_SIZE

                            val expandedBuffer = ByteArray(newSize)

                            System.arraycopy(tempBuffer, 0, expandedBuffer, 0,
                                    tempBuffer.size)

                            tempBuffer = expandedBuffer

                        } while (true)

                        byteBuffer = ByteArray(bufferOffset)

                        System.arraycopy(tempBuffer, 0, byteBuffer, 0, bufferOffset)

                    } else {

                        byteBuffer = ByteArray(contentSize.takeIf { it != null } ?: 0)

                        var remainingLength = contentSize.takeIf { it != null } ?: 0

                        var bufferOffset = 0

                        while (remainingLength > 0) {

                            val readResult = byteStream!!.read(
                                    byteBuffer,
                                    bufferOffset,
                                    remainingLength)

                            if (readResult < 0) {
                                throw EOFException()
                            }

                            bufferOffset += readResult

                            remainingLength -= readResult

                            if (Thread.interrupted()) {
                                throw InterruptedException()
                            }
                        }
                    }

                    if (Thread.interrupted()) {
                        throw InterruptedException()
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                    return

                } finally {

                    if (byteStream != null) {
                        try {
                            byteStream.close()
                        } catch (e: Exception) {

                        }

                    }
                }
            }


            mPlacesTask.byteBuffer = byteBuffer

            mPlacesTask.handleDownloadState(HTTP_STATE_COMPLETED)

        } catch (e1: InterruptedException) {

            // Does nothing

            // In all cases, handle the results
        } finally {

            if (byteBuffer == null) {
                mPlacesTask.handleDownloadState(HTTP_STATE_FAILED)
            }

            mPlacesTask.currentThread = null

            Thread.interrupted()
        }

    }
}