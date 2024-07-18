package com.example.pruebatecnicaapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicaapp.adapters.PeliculasAdapter
import com.example.pruebatecnicaapp.connectivity.api.NetworkResponse
import com.example.pruebatecnicaapp.databinding.ActivityListaPeliculasBinding
import com.example.pruebatecnicaapp.models.Data
import com.example.pruebatecnicaapp.models.Result
import com.example.pruebatecnicaapp.ui.viewmodels.ListaPeliculasViewModel
import com.example.pruebatecnicaapp.utils.Constants.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaPeliculas : AppCompatActivity() {

    private lateinit var binding: ActivityListaPeliculasBinding
    private lateinit var vm: ListaPeliculasViewModel
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPeliculasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(ListaPeliculasViewModel::class.java)
        auth = Firebase.auth
        binding.btnLogout.setOnClickListener(){
            mostrarDialogoCerrarSesion()
        }
        getList()
    }
    fun getList(){
        vm.getListPeliculas().observe(this){ response ->
            when (response.status) {
                NetworkResponse.Status.LOADING -> {
                }
                NetworkResponse.Status.SUCCESS -> {
                    response.data?.let {
                        val response = Gson().fromJson(it.string(), Data::class.java)
                        val models = response.results
                        try {
                            loadData(models)
                        }catch (e: Throwable){
                            e.printStackTrace()
                        }
                    }
                }
                NetworkResponse.Status.ERROR -> {
                    Log.d(TAG, response.toString())
                }
            }
        }
    }

    fun loadData(models: List<Result>){
        val peliculasAdapter = PeliculasAdapter(models, this)
        binding.recyclerview.adapter = peliculasAdapter
    }

    private fun mostrarDialogoCerrarSesion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar Sesión")
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")

        builder.setPositiveButton("Sí") { dialog, which ->
            // Cierra la sesión del usuario
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}