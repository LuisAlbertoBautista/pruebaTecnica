package com.example.pruebatecnicaapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "evento")
data class Evento(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var nombre: String = "",
    var fecha: String = "",
    var descripcion: String = "",
    var status: String = ""
)
