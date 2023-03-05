package com.vonbrank.sunnyweather.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vonbrank.sunnyweather.ui.page.SearchPlace
import com.vonbrank.sunnyweather.ui.page.WeatherDetail
import com.vonbrank.sunnyweather.ui.theme.SunnyWeatherTheme

@Composable
fun App() {
    SunnyWeatherTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "searchPlace") {
                composable("searchPlace") {
                    SearchPlace(onClickPlaceItem = {
                        navController.navigate("weatherDetail")
                    })
                }
                composable("weatherDetail") {
                    WeatherDetail()
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}