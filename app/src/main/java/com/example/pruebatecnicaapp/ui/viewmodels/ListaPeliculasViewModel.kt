package com.example.pruebatecnicaapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pruebatecnicaapp.connectivity.api.NetworkResponse
import com.example.pruebatecnicaapp.connectivity.api.dataAccess
import com.example.pruebatecnicaapp.connectivity.repository.ApiRepository
import com.example.pruebatecnicaapp.utils.Constants.BASE_URL
import com.example.pruebatecnicaapp.utils.Constants.TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class ListaPeliculasViewModel @Inject constructor(private val api: ApiRepository) : ViewModel() {
    fun getListPeliculas(): LiveData<NetworkResponse<ResponseBody>> {
        return dataAccess { api.simpleGet(BASE_URL+"movie/now_playing", "Bearer $TOKEN")}
    }
}