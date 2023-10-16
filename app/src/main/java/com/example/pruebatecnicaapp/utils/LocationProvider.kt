package com.example.pruebatecnicaapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*

class LocationProvider(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(locationCallback: (Location) -> Unit) {
        val locationRequest = LocationRequest.create().apply {
            interval = 1000 // Intervalo de actualización de ubicación en milisegundos
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    locationCallback(location)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
}