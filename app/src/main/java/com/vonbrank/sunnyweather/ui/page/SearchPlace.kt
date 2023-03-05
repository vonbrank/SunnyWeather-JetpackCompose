package com.vonbrank.sunnyweather.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vonbrank.sunnyweather.logic.dao.placeList
import com.vonbrank.sunnyweather.ui.component.PlaceCard
import com.vonbrank.sunnyweather.ui.theme.Gray100

@Composable
fun SearchPlace(onClickPlaceItem: () -> Unit = {}) {

    Column() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            var value by remember {
                mutableStateOf("")
            }
            TextField(
                value = value,
                onValueChange = { newValue -> value = newValue },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                placeholder = {
                    Text(text = "输入地址")
                }
            )
        }

        Surface(
            modifier = Modifier
                .weight(1f)
                .background(Gray100)
                .padding(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Gray100)
            ) {
                items(placeList) { place ->
                    PlaceCard(
                        name = place.name,
                        address = place.address,
                        modifier = Modifier.clickable { onClickPlaceItem() })
                }
            }
        }
    }
}