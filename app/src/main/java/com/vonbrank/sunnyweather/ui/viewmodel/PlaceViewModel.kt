package com.vonbrank.sunnyweather.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vonbrank.sunnyweather.logic.Repository
import com.vonbrank.sunnyweather.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val _searchLiveData = MutableLiveData<String>("")

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(_searchLiveData) { query ->
//        Log.d("PlaceViewModel", "query value = $query")
        Repository.searchPlace(query)
    }

    fun searchPlaces(query: String) {
        _searchLiveData.value = query
    }
}