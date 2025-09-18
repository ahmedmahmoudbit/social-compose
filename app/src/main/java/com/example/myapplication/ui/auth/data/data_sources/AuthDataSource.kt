package com.example.myapplication.ui.auth.data.data_sources

import android.util.Log
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.domain.repositories.AuthRepo
import com.example.compose.utils.service.ApiService
import com.example.myapplication.ui.auth.data.models.ForgetPasswordRequest
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.RegisterRequest
import com.example.myapplication.ui.auth.data.models.VerifyPasswordRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val apiService: ApiService
) : AuthRepo {

    override suspend fun login(userData: LoginRequest): Response<LoginResponse> {
        return apiService.login(userData)
    }

    override suspend fun register(userData: RegisterRequest): Response<MessageResponse> {
        return try {
            val (requestBodyMap, avatarPart) = prepareRegisterRequest(userData)
            val response = apiService.register(
                fullName = requestBodyMap["fullName"] ?: throw IllegalArgumentException("fullName is null"),
                username = requestBodyMap["username"] ?: throw IllegalArgumentException("username is null"),
                email = requestBodyMap["email"] ?: throw IllegalArgumentException("email is null"),
                password = requestBodyMap["password"] ?: throw IllegalArgumentException("password is null"),
                phone = requestBodyMap["phone"] ?: throw IllegalArgumentException("phoneNumber is null"),
                avatar = avatarPart
            )
            // Log error body if not successful
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthDataSource", "Error response body: $errorBody")
            } else {
                Log.d("AuthDataSource", "Success response body: ${response.body()}")
            }
            response
        } catch (e: Exception) {
            Log.e("AuthDataSource", "Error in register: ${e.message}", e)
            throw e
        }
    }

    override suspend fun forgetPassword(data: ForgetPasswordRequest): Response<MessageResponse> {
        return try {
            val response = apiService.forgetPassword(data)
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthDataSource", "forgetPassword Error response body: $errorBody")
            } else {
                Log.d("AuthDataSource", "forgetPassword Success response body: ${response.body()}")
            }
            response
        } catch (e: Exception) {
            Log.e("AuthDataSource", "Error in forgetPassword: ${e.message}", e)
            throw e
        }
    }

    override suspend fun verifyPassword(data: VerifyPasswordRequest): Response<MessageResponse> {
        return try {
            val response = apiService.verifyPassword(data)
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthDataSource", "forgetPassword Error response body: $errorBody")
            } else {
                Log.d("AuthDataSource", "forgetPassword Success response body: ${response.body()}")
            }
            response
        } catch (e: Exception) {
            Log.e("AuthDataSource", "Error in forgetPassword: ${e.message}", e)
            throw e
        }
    }

}

fun prepareRegisterRequest(registerRequest: RegisterRequest): Pair<Map<String, RequestBody>, MultipartBody.Part?> {
    return try {
        Log.d("AuthDataSource", "Preparing register request...")
        
        val requestBodyMap = mutableMapOf<String, RequestBody>()
        
        // Validate required fields
        if (registerRequest.fullName.isBlank()) {
            throw IllegalArgumentException("Full name cannot be empty")
        }
        if (registerRequest.username.isBlank()) {
            throw IllegalArgumentException("Username cannot be empty")
        }
        if (registerRequest.email.isBlank()) {
            throw IllegalArgumentException("Email cannot be empty")
        }
        if (registerRequest.password.isBlank()) {
            throw IllegalArgumentException("Password cannot be empty")
        }
        if (registerRequest.phone.isBlank()) {
            throw IllegalArgumentException("Phone number cannot be empty")
        }
        
        // Create request bodies
        requestBodyMap["fullName"] = registerRequest.fullName.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["username"] = registerRequest.username.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["email"] = registerRequest.email.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["password"] = registerRequest.password.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["phone"] = registerRequest.phone.toRequestBody("text/plain".toMediaTypeOrNull())
        
        val avatarPart = registerRequest.avatar
        
        Log.d("AuthDataSource", "Register request prepared successfully")
        
        Pair(requestBodyMap, avatarPart)
    } catch (e: Exception) {
        Log.e("AuthDataSource", "Error preparing register request: ${e.message}", e)
        throw e
    }
}

