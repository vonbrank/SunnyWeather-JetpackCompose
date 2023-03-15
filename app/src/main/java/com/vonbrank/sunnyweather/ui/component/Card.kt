package com.vonbrank.sunnyweather.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vonbrank.sunnyweather.R
import com.vonbrank.sunnyweather.logic.model.DailyResponse
import com.vonbrank.sunnyweather.logic.model.getSky
import com.vonbrank.sunnyweather.ui.theme.Gray600
import java.text.SimpleDateFormat
import java.util.*

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
fun ForecastCard(daily: DailyResponse.Daily, modifier: Modifier = Modifier) {
    TitleCard("预报", modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            val days = daily.skycon.size
            for (i in 0 until days) {
                val skycon = daily.skycon[i]
                val simpleDateFormat = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )
                val sky = getSky(skycon.value)
                val temperature = daily.temperature[i]
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = simpleDateFormat.format(skycon.date),
                        color = Gray600,
                        modifier = Modifier.weight(4f)
                    )

                    Image(
                        painterResource(id = sky.icon),
                        contentDescription = "Day type icon",
                        modifier = Modifier.width(20.dp)
                    )
                    Text(
                        text = sky.info, color = Gray600,
                        modifier = Modifier.weight(3f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} °C",
                        color = Gray600,
                        modifier = Modifier.weight(3f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

data class LifeIndexItemProps(
    val name: String,
    val description: String,
    @DrawableRes val iconId: Int
)

@Composable
fun LifeIndexCard(daily: DailyResponse.Daily, modifier: Modifier = Modifier) {

    val lifeIndex = daily.lifeIndex
    val lifeIndexList = listOf(
        LifeIndexItemProps("感冒", description = lifeIndex.coldRisk[0].desc, R.drawable.ic_coldrisk),
        LifeIndexItemProps("穿衣", description = lifeIndex.dressing[0].desc, R.drawable.ic_dressing),
        LifeIndexItemProps(
            "实时紫外线",
            description = lifeIndex.ultraviolet[0].desc,
            R.drawable.ic_ultraviolet
        ),
        LifeIndexItemProps(
            "洗车",
            description = lifeIndex.carWashing[0].desc,
            R.drawable.ic_carwashing
        ),
    )

    val columnCount = 2

    TitleCard(title = "生活指数", modifier = modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            for (i in lifeIndexList.indices step columnCount) {
                Row() {
                    for (j in i until i + columnCount) {
                        if (j < lifeIndexList.size) {
                            LifeIndexItem(lifeIndexList[j], modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LifeIndexItem(props: LifeIndexItemProps, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        contentColor = MaterialTheme.colors.onSurface
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painterResource(id = props.iconId),
                contentDescription = "Life index icon",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = props.name, style = MaterialTheme.typography.body2)
                Text(text = props.description, style = MaterialTheme.typography.h6)
            }
        }
    }
}