package com.vonbrank.sunnyweather.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vonbrank.sunnyweather.R
import com.vonbrank.sunnyweather.ui.theme.Gray600

@Composable
fun TitleCard(title: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp), elevation = 2.dp) {
        Column(
            modifier = modifier.padding(vertical = 32.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h5)
            content()
        }
    }
}

@Composable
fun PlaceCard(name: String, address: String, modifier: Modifier = Modifier) {
    TitleCard(name, modifier = modifier) {
        Text(text = address, style = MaterialTheme.typography.body2)
    }

}

@Composable
fun ForecastCard(modifier: Modifier = Modifier) {
    TitleCard("预报", modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            for (i in 1..5) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "2019-10-25", color = Gray600)
                    Image(
                        painterResource(id = R.drawable.ic_clear_day),
                        contentDescription = "Day type icon"
                    )
                    Text(text = "多云", color = Gray600)
                    Text(text = "4 ~ 13 °C", color = Gray600)
                }
            }
        }
    }
}

@Composable
fun LifeIndexCard(modifier: Modifier = Modifier) {
    TitleCard(title = "生活指数", modifier = modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row() {
                LifeIndexItem(R.drawable.ic_coldrisk, "感冒", "极易发", modifier = Modifier.weight(1f))
                LifeIndexItem(R.drawable.ic_coldrisk, "感冒", "极易发", modifier = Modifier.weight(1f))
            }
            Row() {
                LifeIndexItem(R.drawable.ic_coldrisk, "感冒", "极易发", modifier = Modifier.weight(1f))
                LifeIndexItem(R.drawable.ic_coldrisk, "感冒", "极易发", modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun LifeIndexItem(iconId: Int, name: String, description: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Image(
            painterResource(id = iconId),
            contentDescription = "Life index icon"
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = name, style = MaterialTheme.typography.body2, color = Gray600)
            Text(text = description, style = MaterialTheme.typography.h6)
        }
    }
}