package com.example.pruebatecnicaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pruebatecnicaapp.databinding.ActivityCreateEventBinding
import com.example.pruebatecnicaapp.db.AppDatabase
import com.example.pruebatecnicaapp.db.Evento
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
@AndroidEntryPoint
class CreateEvent : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding
    @Inject
    lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCrearEvento.setOnClickListener(){
            if (binding.editTextNombreEvento.text.trim() != ""){
                val nuevoEvento = Evento()
                nuevoEvento.nombre = binding.editTextNombreEvento.text.trim().toString()
                nuevoEvento.fecha = Date().toString().trim()
                nuevoEvento.descripcion= binding.editTextDescripcion.text.trim().toString()
                GlobalScope.launch(Dispatchers.IO) {
                    appDatabase.eventoDao().insertarEvento(nuevoEvento)
                }
                finish()
            }else {
                Toast.makeText(this, "Debes poner un nombre a tu evento", Toast.LENGTH_SHORT).show()
            }
        }
    }
}