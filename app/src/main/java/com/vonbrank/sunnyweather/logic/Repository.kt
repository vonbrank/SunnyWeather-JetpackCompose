package com.vonbrank.sunnyweather.logic

import androidx.lifecycle.liveData
import com.vonbrank.sunnyweather.logic.model.Place
import com.vonbrank.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlace(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            if (query == "") {
                Result.success(ArrayList<Place>())
            } else {
                val placeResponse = SunnyWeatherNetwork.searchPlace(query)
                if (placeResponse.status == "ok") {
                    val places = placeResponse.places
                    Result.success(places)
                } else {
                    Result.failure(RuntimeException("response status is ${placeResponse.status}"))
                }
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}