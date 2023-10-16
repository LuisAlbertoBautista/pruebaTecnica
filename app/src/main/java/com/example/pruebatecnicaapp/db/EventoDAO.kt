package com.example.pruebatecnicaapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface EventoDAO {
        @Insert
        suspend fun insertarEvento(evento: Evento)

        @Query("SELECT * FROM evento")
        suspend fun obtenerTodosLosEventos(): List<Evento>
}