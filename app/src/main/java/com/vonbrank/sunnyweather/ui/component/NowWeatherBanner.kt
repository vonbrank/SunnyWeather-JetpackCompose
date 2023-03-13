package com.vonbrank.sunnyweather.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vonbrank.sunnyweather.R


@Composable
fun NowWeatherBanner(
    placeName: String,
    currentTemperatureText: String,
    currentSkyText: String,
    currentAqiText: String,
    @DrawableRes currentBackgroundResourceId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(540.dp)
    ) {
        Image(
            painterResource(id = currentBackgroundResourceId),
            contentDescription = "Weather background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomCenter
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
        ) {

            Text(
                text = placeName,
                modifier = Modifier.align(Alignment.TopCenter),
                color = Color.White,
                fontSize = 20.sp
            )

            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = currentTemperatureText,
                    Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h1,
                    color = Color.White,
                    fontWeight = FontWeight(600)
                )

                Text(
                    text = "$currentSkyText | $currentAqiText",
                    Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

    }
}