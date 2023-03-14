package com.vonbrank.sunnyweather.ui.page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vonbrank.sunnyweather.R
import com.vonbrank.sunnyweather.SunnyWeatherApplication
import com.vonbrank.sunnyweather.logic.model.Place
import com.vonbrank.sunnyweather.ui.component.PlaceCard
import com.vonbrank.sunnyweather.ui.theme.Gray100
import com.vonbrank.sunnyweather.ui.viewmodel.PlaceViewModel

@Composable
fun SearchPlace(
    onClickPlaceItem: (lng: String, lat: String, placeName: String) -> Unit = { _: String, _: String, _: String ->

    },
    placeViewModel: PlaceViewModel = viewModel()
) {
    Column() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondary)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            var query by remember {
                mutableStateOf("")
            }

            TextField(
                value = query,
                onValueChange = { newValue ->
                    query = newValue
                    Log.d("Search place", "new value = $newValue")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = "输入地址")
                },
                leadingIcon = {
                    IconButton(onClick = { placeViewModel.searchPlaces(query) }) {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search icon")
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear button icon"
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    placeViewModel.searchPlaces(query)
                    query = ""
                })
            )
        }

        val placeListResult by placeViewModel.placeLiveData.observeAsState()
        val placeList by produceState<List<Place>>(
            initialValue = ArrayList(),
            placeListResult
        ) {
            if (placeListResult == null) {
                value = ArrayList()
            } else {
                val places = placeListResult?.getOrNull()
                if (places != null) {
                    value = places;
                } else {
                    value = ArrayList()
                    Toast.makeText(
                        SunnyWeatherApplication.context,
                        "未能查询到任何地点",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    placeListResult?.exceptionOrNull()?.printStackTrace()
                }
            }
        }

        if (placeList.isNotEmpty()) {

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
                            modifier = Modifier.clickable {
                                placeViewModel.savePlace(place)
                                onClickPlaceItem(
                                    place.location.lng,
                                    place.location.lat,
                                    place.name
                                )
                            })
                    }
                }
            }
        } else {

            Image(
                painter = painterResource(id = R.drawable.bg_place),
                contentDescription = "Search background",
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter
            )
        }
    }
}