package com.vonbrank.sunnyweather.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
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
fun NowWeatherBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(540.dp)
    ) {
        Image(
            painterResource(id = R.drawable.bg_clear_day),
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
                text = "City Name",
                modifier = Modifier.align(Alignment.TopCenter),
                color = Color.White,
                fontSize = 20.sp
            )

            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "10 °C",
                    Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h1,
                    color = Color.White,
                    fontWeight = FontWeight(600)
                )

                Text(
                    text = "Weather Name | Air Index",
                    Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

    }
}