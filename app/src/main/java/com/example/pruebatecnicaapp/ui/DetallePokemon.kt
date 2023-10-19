package com.example.pruebatecnicaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicaapp.R
import com.example.pruebatecnicaapp.connectivity.api.NetworkResponse
import com.example.pruebatecnicaapp.databinding.ActivityDetallePokemonBinding
import com.example.pruebatecnicaapp.models.DetallePokemonResponse
import com.example.pruebatecnicaapp.models.ListPokemonResponse
import com.example.pruebatecnicaapp.ui.viewmodels.DetallePokemonViewModel
import com.example.pruebatecnicaapp.ui.viewmodels.ListaPokemonViewModel
import com.example.pruebatecnicaapp.utils.Constants
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetallePokemon : AppCompatActivity() {
    lateinit var binding: ActivityDetallePokemonBinding
    private lateinit var vm: DetallePokemonViewModel
    private var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(DetallePokemonViewModel::class.java)
        id = intent.extras?.getString("id","").toString()
        getList()
    }
    fun getList(){
        vm.getDetallePokemon(id).observe(this){ response ->
            when (response.status) {
                NetworkResponse.Status.LOADING -> {
                }
                NetworkResponse.Status.SUCCESS -> {
                    response.data?.let {
                        val response = Gson().fromJson(it.string(), DetallePokemonResponse::class.java)
                        try {
                            initDetalle(response)
                        }catch (e: Throwable){
                            e.printStackTrace()
                        }
                    }
                }
                NetworkResponse.Status.ERROR -> {
                    Log.d(Constants.TAG, response.toString())
                }
            }
        }
    }

    fun initDetalle (response: DetallePokemonResponse){
        Picasso.with(this).load("${Constants.BASE_IMAGE}$id.png").into(binding.imgPokemon)
        binding.tvEspecie.text = response.species.name
        binding.tvHabilidad.text = response.abilities[0].ability.name
        binding.tvNombre.text = response.name
    }

}