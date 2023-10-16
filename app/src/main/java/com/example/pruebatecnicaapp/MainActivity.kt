package com.example.pruebatecnicaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicaapp.databinding.ActivityMainBinding
import com.example.pruebatecnicaapp.utils.LocationProvider
import com.example.pruebatecnicaapp.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var vm: MainActivityViewModel
    private lateinit var locationProvider: LocationProvider
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        locationProvider = LocationProvider(this)

        // Solicitar permisos de ubicación
        if (hasLocationPermissions()) {
            startLocationUpdates()
        } else {
            requestLocationPermissions()
        }
    }

    private fun startLocationUpdates() {
        vm.startLocationUpdates(locationProvider)
        updateLocationTextView()
    }

    private fun updateLocationTextView() {
        vm.mutableDataLocation.observe(this){
            binding.tvUbicacion.text = vm.mutableDataLocation.value
        }
    }

    private fun hasLocationPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            42
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 42) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            }
        }
    }
}