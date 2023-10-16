package com.example.pruebatecnicaapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Evento::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventoDao(): EventoDAO
}