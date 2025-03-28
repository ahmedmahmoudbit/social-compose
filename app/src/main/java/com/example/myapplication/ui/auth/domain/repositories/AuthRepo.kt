package com.example.myapplication.ui.auth.domain.repositories
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.RegisterRequest
import com.example.myapplication.ui.auth.data.models.RegisterResponse
import retrofit2.Response

interface AuthRepo {
    suspend fun login(userData: LoginRequest) : Response<LoginResponse>
    suspend fun register(userData: RegisterRequest) : Response<RegisterResponse>
}