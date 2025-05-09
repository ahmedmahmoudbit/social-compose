package com.example.myapplication.ui.home.data.repositories

import android.content.Context
import android.util.Log
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.data_sources.AuthDataSource
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.models.RegisterRequest
import com.example.myapplication.ui.auth.data.models.RegisterResponse
import com.example.myapplication.utils.service.model.ErrorResponse
import com.example.utils.CoreUtility
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepoImp @Inject constructor(
    private val dataSource: AuthDataSource,
    private val context: Context
) {

      fun login(userData: LoginRequest): Flow<LoginState<LoginResponse>> {
        return flow {
            emit(LoginState.Loading)

            if (!CoreUtility.isInternetConnection(context)) {
                emit(LoginState.Error(error = "No internet connection"))
                return@flow
            }

            val response = dataSource.login(userData)

            if (response.isSuccessful && response.body() != null) {
                    emit(LoginState.Success(response.body()!!))

            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (errorBody.isNullOrEmpty()) {
                    response.message() ?: "Error Fetching Data"
                } else {
                    try {
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorResponse.message ?: "Error Fetching Data"
                    } catch (e: Exception) {
                        errorBody
                    }
                }
                emit(LoginState.Error(error = errorMessage))
            }
        }.catch { e->
            emit(LoginState.Error(error = e.localizedMessage ?: "Error Fetching Data"))

        }
    }

      fun register(userData: RegisterRequest): Flow<LoginState<RegisterResponse>> {
        return flow {
            emit(LoginState.Loading)

            if (!CoreUtility.isInternetConnection(context)) {
                emit(LoginState.Error(error = "No internet connection"))
                return@flow
            }

            val response = dataSource.register(userData)

            if (response.isSuccessful && response.body() != null) {
                emit(LoginState.Success(response.body()!!))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (errorBody.isNullOrEmpty()) {
                    response.message() ?: "Error Fetching Data"
                } else {
                    try {
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorResponse.message ?: "Error Fetching Data"
                    } catch (e: Exception) {
                        errorBody
                    }
                }
                emit(LoginState.Error(error = errorMessage))
            }
        }.catch { e->

            Log.i("AUTH", "register: ------ ${e.localizedMessage}")

            emit(LoginState.Error(error = e.localizedMessage ?: "Error Fetching Data"))

        }
    }

}
