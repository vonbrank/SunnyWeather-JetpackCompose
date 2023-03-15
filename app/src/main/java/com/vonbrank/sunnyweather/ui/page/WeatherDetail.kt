package com.vonbrank.sunnyweather.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vonbrank.sunnyweather.ui.component.ForecastCard
import com.vonbrank.sunnyweather.ui.component.LifeIndexCard
import com.vonbrank.sunnyweather.ui.component.NowWeatherBanner
import com.vonbrank.sunnyweather.ui.viewmodel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vonbrank.sunnyweather.logic.model.getSky
import com.vonbrank.sunnyweather.ui.viewmodel.PlaceViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherDetail(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = viewModel(),
    placeViewModel: PlaceViewModel = viewModel(),
) {
    val refreshing by weatherViewModel.refreshing.observeAsState()

    DisposableEffect(weatherViewModel.placeName) {
        weatherViewModel.refreshWeather(weatherViewModel.locationLng, weatherViewModel.locationLat)
        onDispose {
        }
    }

    val weather by weatherViewModel.weatherLiveData.observeAsState()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            SearchPlace(
                onClickPlaceItem = { lng: String, lat: String, placeName: String ->
                    weatherViewModel.locationLng = lng
                    weatherViewModel.locationLat = lat
                    weatherViewModel.placeName = placeName
                    weatherViewModel.refreshWeather(
                        weatherViewModel.locationLng,
                        weatherViewModel.locationLat
                    )
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                },
                placeViewModel = placeViewModel
            )
        },
        modifier = modifier
    ) { paddingValues ->

        val pullRefreshState = rememberPullRefreshState(refreshing ?: false, onRefresh = {
            weatherViewModel.setRefreshing(true)
            weatherViewModel.refreshWeather(
                weatherViewModel.locationLng,
                weatherViewModel.locationLat
            )
        })

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {

                if (weather != null) {

                    val realtime = weather!!.realtime
                    val daily = weather!!.daily

                    item {

                        NowWeatherBanner(
                            placeName = weatherViewModel.placeName,
                            currentTemperatureText = "${realtime.temperature.toInt()} °C",
                            currentSkyText = getSky(realtime.skycon).info,
                            currentAqiText = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}",
                            currentBackgroundResourceId = getSky(realtime.skycon).bg,
                            {
                                scope.launch {
                                    scaffoldState.drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
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
                } else {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            CircularProgressIndicator()
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing ?: false,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}