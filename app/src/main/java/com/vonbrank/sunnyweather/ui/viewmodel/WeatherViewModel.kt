package com.vonbrank.sunnyweather.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vonbrank.sunnyweather.SunnyWeatherApplication
import com.vonbrank.sunnyweather.logic.Repository
import com.vonbrank.sunnyweather.logic.model.Location

class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    private val weatherResultLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    val weatherLiveData = Transformations.map(weatherResultLiveData) { result ->

        if (result == null) {
            null
        } else {
            _refreshing.value = false

            val weather = result.getOrNull()
            if (weather != null) {
                Toast.makeText(SunnyWeatherApplication.context, "天气信息已更新", Toast.LENGTH_SHORT)
                    .show()
                weather
            } else {
                Toast.makeText(
                    SunnyWeatherApplication.context,
                    "无法成功获取天气信息",
                    Toast.LENGTH_SHORT
                )
                    .show()
                result.exceptionOrNull()?.printStackTrace()
                null
            }
        }
    }

    private val _refreshing = MutableLiveData<Boolean>(false)

    val refreshing: LiveData<Boolean>
        get() = _refreshing

    fun setRefreshing(newValue: Boolean) {
        _refreshing.value = newValue
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }
}