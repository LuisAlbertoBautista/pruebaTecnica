package com.example.pruebatecnicaapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.pruebatecnicaapp.databinding.ActivityDetallePeliculaBinding
import com.example.pruebatecnicaapp.models.Result
import com.example.pruebatecnicaapp.ui.viewmodels.DetallePeliculaViewModel
import com.example.pruebatecnicaapp.utils.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetallePeliculas : AppCompatActivity() {
    lateinit var binding: ActivityDetallePeliculaBinding
    private lateinit var vm: DetallePeliculaViewModel
    private lateinit var result : Result
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePeliculaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(DetallePeliculaViewModel::class.java)
        result = intent.extras?.getSerializable("PELICULA") as Result
        initDetalle()
    }


    fun initDetalle (){
        Picasso.with(this).load("${Constants.BASE_IMAGE}${result.poster_path}").into(binding.imgPelicula)
        binding.tvTitulo.text = result.original_title
        binding.tvFechaEstreno.text = result.release_date
        binding.tvDescripcion.text = result.overview
    }

}