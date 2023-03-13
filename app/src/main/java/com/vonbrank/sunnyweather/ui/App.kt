package com.vonbrank.sunnyweather.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vonbrank.sunnyweather.ui.page.SearchPlace
import com.vonbrank.sunnyweather.ui.page.WeatherDetail
import com.vonbrank.sunnyweather.ui.theme.SunnyWeatherTheme
import com.vonbrank.sunnyweather.ui.viewmodel.WeatherViewModel

@Composable
fun App() {
    SunnyWeatherTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()

            val weatherViewModel: WeatherViewModel = viewModel()

            NavHost(navController = navController, startDestination = "searchPlace") {
                composable("searchPlace") {
                    SearchPlace(onClickPlaceItem = { lng: String, lat: String, placeName: String ->
                        navController.navigate("weatherDetail/$lng/$lat/$placeName")
                    })
                }
                composable(
                    "weatherDetail/{lng}/{lat}/{placeName}",
                    arguments = listOf(
                        navArgument("lng") { type = NavType.StringType },
                        navArgument("lat") { type = NavType.StringType },
                        navArgument("placeName") { type = NavType.StringType },
                    )
                ) {
                    weatherViewModel.locationLng = it.arguments?.getString("lng") ?: ""
                    weatherViewModel.locationLat = it.arguments?.getString("lat") ?: ""
                    weatherViewModel.placeName = it.arguments?.getString("placeName") ?: ""
                    WeatherDetail(viewModel = weatherViewModel)
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}