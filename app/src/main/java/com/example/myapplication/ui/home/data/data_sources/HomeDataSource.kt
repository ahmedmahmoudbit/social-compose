package com.example.myapplication.ui.home.data.data_sources

import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.compose.utils.service.ApiService
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.RegisterRequest
import com.example.myapplication.ui.auth.data.models.RegisterResponse
import com.example.myapplication.ui.home.domain.repositories.HomeRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class HomeRepoDataSource @Inject constructor(
    private val apiService: ApiService
) : HomeRepo {

    override suspend fun login(userData: LoginRequest): Response<LoginResponse> {
        return apiService.login(userData)
    }

    override suspend fun register(userData: RegisterRequest): Response<RegisterResponse> {
        val (requestBodyMap, avatarPart) = prepareRegisterRequest(userData)
        return apiService.register(
            fullName = requestBodyMap["fullName"]!!,
            username = requestBodyMap["username"]!!,
            email = requestBodyMap["email"]!!,
            password = requestBodyMap["password"]!!,
            phoneNumber = requestBodyMap["phoneNumber"]!!,
            avatar = avatarPart
        )
    }
}

fun prepareRegisterRequest(registerRequest: RegisterRequest): Pair<Map<String, RequestBody>, MultipartBody.Part?> {
    val requestBodyMap = mutableMapOf<String, RequestBody>()
    requestBodyMap["fullName"] = registerRequest.fullName.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["username"] = registerRequest.username.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["email"] = registerRequest.email.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["password"] = registerRequest.password.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["phoneNumber"] = registerRequest.phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
    val avatarPart = registerRequest.avatar
    return Pair(requestBodyMap, avatarPart)
}

