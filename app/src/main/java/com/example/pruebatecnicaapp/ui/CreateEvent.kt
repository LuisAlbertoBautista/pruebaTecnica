package com.example.pruebatecnicaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pruebatecnicaapp.R
import com.example.pruebatecnicaapp.databinding.ActivityMainBinding

class CreateEvent : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}