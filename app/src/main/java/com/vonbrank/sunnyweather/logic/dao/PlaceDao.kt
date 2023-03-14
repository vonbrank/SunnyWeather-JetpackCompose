package com.vonbrank.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.vonbrank.sunnyweather.SunnyWeatherApplication
import com.vonbrank.sunnyweather.logic.model.Place

object PlaceDao {

    private const val PLACE_NAME = "place"

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString(PLACE_NAME, Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString(PLACE_NAME, "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains(PLACE_NAME)

    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}