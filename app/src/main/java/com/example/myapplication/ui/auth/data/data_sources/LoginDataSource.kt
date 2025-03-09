package com.example.myapplication.ui.auth.data.data_sources

import com.example.compose.clean_art.retrofit.data.models.LoginResponse
import com.example.myapplication.ui.auth.domain.repositories.AuthRepo
import com.example.compose.utils.service.ApiService
import com.example.myapplication.ui.auth.data.models.LoginRequest
import retrofit2.Response
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val apiService: ApiService
) : AuthRepo {

    override suspend fun login(userData: LoginRequest): Response<LoginResponse> {
        return apiService.login(userData)
    }

//    override suspend fun register(country: String): Response<NewsResponse> {
//        return apiService.getNews(country)
//    }

}