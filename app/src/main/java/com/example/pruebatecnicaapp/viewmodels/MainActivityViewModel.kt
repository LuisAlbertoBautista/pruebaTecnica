package com.example.pruebatecnicaapp.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebatecnicaapp.utils.LocationProvider
import kotlin.math.round


class MainActivityViewModel : ViewModel() {
    var mutableDataLocation = MutableLiveData<String>()

    fun startLocationUpdates( locationProvider: LocationProvider) {
        locationProvider.startLocationUpdates { location ->
            updateLocationTextView(location)
        }
    }

    private fun updateLocationTextView(location: Location) {
        val latitude = round(location.latitude * 1000.0) / 1000.0
        val longitude = round(location.longitude * 1000.0) / 1000.0
        val speed = round(location.speed * 3.6 * 100.0) / 100.0  // m/s to km/h
        val locationText = "Ubicación: $latitude, $longitude, Velocidad: $speed km/h"
        mutableDataLocation.value =  locationText
    }
}