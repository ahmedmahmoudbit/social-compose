package com.example.myapplication.simple_api.api

import com.example.myapplication.simple_api.model.DataModel
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getData(): List<DataModel>
}