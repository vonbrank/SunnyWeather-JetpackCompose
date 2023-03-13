package com.vonbrank.sunnyweather.ui.page

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vonbrank.sunnyweather.ui.component.ForecastCard
import com.vonbrank.sunnyweather.ui.component.LifeIndexCard
import com.vonbrank.sunnyweather.ui.component.NowWeatherBanner
import com.vonbrank.sunnyweather.ui.theme.Gray100
import com.vonbrank.sunnyweather.ui.viewmodel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vonbrank.sunnyweather.SunnyWeatherApplication
import com.vonbrank.sunnyweather.logic.model.RealtimeResponse
import com.vonbrank.sunnyweather.logic.model.Weather
import com.vonbrank.sunnyweather.logic.model.getSky

@Composable
fun WeatherDetail(modifier: Modifier = Modifier, viewModel: WeatherViewModel = viewModel()) {

    var loading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(true) {
        loading = true
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        loading = false
    }

    val weatherResult by viewModel.weatherLiveData.observeAsState()
    val weather by produceState<Weather?>(initialValue = null, weatherResult) {
        if (weatherResult == null) {
        } else {
            val weather = weatherResult!!.getOrNull()
            if (weather != null) {
                value = weather
            } else {
                Toast.makeText(SunnyWeatherApplication.context, "无法成功获取天气信息", Toast.LENGTH_SHORT)
                    .show()
                weatherResult!!.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Gray100)
        ) {

            if (weather != null && !loading) {

                val realtime = weather!!.realtime
                val daily = weather!!.daily

                item {

                    NowWeatherBanner(
                        placeName = viewModel.placeName,
                        currentTemperatureText = "${realtime.temperature.toInt()} °C",
                        currentSkyText = getSky(realtime.skycon).info,
                        currentAqiText = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}",
                        currentBackgroundResourceId = getSky(realtime.skycon).bg
                    )
                }

                item {

                    Column(
                        modifier = Modifier
                            .padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {

                        ForecastCard(daily)
                        LifeIndexCard(daily)
                    }
                }
            }
        }
    }
}