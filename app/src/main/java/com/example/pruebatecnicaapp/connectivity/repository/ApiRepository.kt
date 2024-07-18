package com.example.pruebatecnicaapp.connectivity.repository


import com.example.pruebatecnicaapp.connectivity.api.ApiResponse
import com.example.pruebatecnicaapp.connectivity.api.ConnectionModel
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val connectionModel: ConnectionModel
): ApiResponse() {
    suspend fun simpleGet(url: String, accessToken:String) = getResult { connectionModel.simpleGet(url, accessToken) }

}