package com.example.pruebatecnicaapp.models

import java.io.Serializable

data class ListPokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
):Serializable