package com.example.pruebatecnicaapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebatecnicaapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener(){
            validateEditText()
        }

    }

    fun validateEditText(){
        val correo = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString().trim()
        if (correo.equals("")){
            Toast.makeText(this, "Debes ingresar tu email", Toast.LENGTH_SHORT).show()
        }else if (pass.isEmpty()){
            Toast.makeText(this, "Debes ingresar tu pass", Toast.LENGTH_SHORT).show()
        }else{
            login(correo,pass)
        }
    }

    fun login(correo: String, pass: String){
        auth.signInWithEmailAndPassword(correo,pass).addOnCompleteListener(this){ task ->
            if (task.isSuccessful) {
                val intent = Intent(this, ListaPokemon::class.java)
                startActivity(intent)
                Toast.makeText(this, "Sesion exitosa", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Ha ocurrido un error al iniciar sesion, intentelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        }
    }

}