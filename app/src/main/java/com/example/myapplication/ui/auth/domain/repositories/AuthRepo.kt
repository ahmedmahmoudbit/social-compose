package com.example.myapplication.ui.auth.domain.repositories
import com.example.compose.clean_art.retrofit.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.LoginRequest
import retrofit2.Response

interface AuthRepo {
    suspend fun login(userData: LoginRequest) : Response<LoginResponse>
}