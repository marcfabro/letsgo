package com.example.poweranimal.letsgo.Application

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.poweranimal.letsgo.R
import com.example.poweranimal.web_lib.Google.LocationService.Places.PlacesHolder
import com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Builder.PlacesRequestBuilder

class MainActivity : AppCompatActivity() {

    val placesHolder: PlacesHolder = PlacesHolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placesHolder.placesUrl = PlacesRequestBuilder("maps.googleapis.com/maps/api/place" ,"123").build()

    }
}
