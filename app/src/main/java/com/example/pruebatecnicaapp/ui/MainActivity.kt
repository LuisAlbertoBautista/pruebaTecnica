package com.example.pruebatecnicaapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicaapp.adapters.EventoAdapter
import com.example.pruebatecnicaapp.databinding.ActivityMainBinding
import com.example.pruebatecnicaapp.db.AppDatabase
import com.example.pruebatecnicaapp.utils.LocationProvider
import com.example.pruebatecnicaapp.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var vm: MainActivityViewModel
    private lateinit var locationProvider: LocationProvider
    private lateinit var binding: ActivityMainBinding

    private lateinit var eventoAdapter: EventoAdapter

    @Inject
    lateinit var appDatabase: AppDatabase


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
        binding.imgAddEvent.setOnClickListener(){
            val intent = Intent(this, CreateEvent::class.java)
            startActivity(intent)
        }
        GlobalScope.launch(Dispatchers.IO) {
            initReciclerView()
        }
    }



    suspend fun initReciclerView(){
        val eventos = appDatabase.eventoDao().obtenerTodosLosEventos()
            eventoAdapter = EventoAdapter(eventos)
            binding.recyclerview.adapter = eventoAdapter
            if (!eventos.isEmpty()){
                binding.lyPrincipal.visibility = View.VISIBLE
                binding.lyNotFoundEvents.visibility = View.GONE
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