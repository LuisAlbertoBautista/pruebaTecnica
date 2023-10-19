package com.example.pruebatecnicaapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicaapp.R
import com.example.pruebatecnicaapp.adapters.PokemonAdapter
import com.example.pruebatecnicaapp.connectivity.api.NetworkResponse
import com.example.pruebatecnicaapp.connectivity.api.convertToObject
import com.example.pruebatecnicaapp.databinding.ActivityListaPokemonBinding
import com.example.pruebatecnicaapp.models.ListPokemonResponse
import com.example.pruebatecnicaapp.models.Result
import com.example.pruebatecnicaapp.ui.viewmodels.ListaPokemonViewModel
import com.example.pruebatecnicaapp.utils.Constants.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaPokemon : AppCompatActivity() {

    private lateinit var binding: ActivityListaPokemonBinding
    private lateinit var vm: ListaPokemonViewModel
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(ListaPokemonViewModel::class.java)
        auth = Firebase.auth
        binding.btnLogout.setOnClickListener(){
            mostrarDialogoCerrarSesion()
        }
        getList()
    }
    fun getList(){
        vm.getListPkemon().observe(this){ response ->
            when (response.status) {
                NetworkResponse.Status.LOADING -> {
                }
                NetworkResponse.Status.SUCCESS -> {
                    response.data?.let {
                        val response = Gson().fromJson(it.string(), ListPokemonResponse::class.java)
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
        val pokemonAdapterPokemon = PokemonAdapter(models, this)
        binding.recyclerview.adapter = pokemonAdapterPokemon
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