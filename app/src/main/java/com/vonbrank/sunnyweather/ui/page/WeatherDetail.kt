package com.vonbrank.sunnyweather.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vonbrank.sunnyweather.ui.component.ForecastCard
import com.vonbrank.sunnyweather.ui.component.LifeIndexCard
import com.vonbrank.sunnyweather.ui.component.NowWeatherBanner
import com.vonbrank.sunnyweather.ui.theme.Gray100

@Composable
fun WeatherDetail(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState();

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            NowWeatherBanner()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(Gray100)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {

                ForecastCard()
                LifeIndexCard()
            }
        }
    }
}