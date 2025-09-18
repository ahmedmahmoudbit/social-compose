package com.example.myapplication.ui.home.data.repositories

import android.content.Context
import com.example.myapplication.simple_api.api.ApiCall
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.data_sources.AuthDataSource
import com.example.myapplication.ui.auth.data.models.AuthState
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.home.data.data_sources.HomeRepoDataSource
import com.example.myapplication.ui.home.data.model.PostResponse
import com.example.myapplication.utils.service.model.ErrorResponse
import com.example.utils.CoreUtility
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepoImp @Inject constructor(
    private val dataSource: HomeRepoDataSource,
    @ApplicationContext private val context: Context
) {
    fun getPosts(): Flow<AuthState<PostResponse>> {
        return ApiCall.call(apiCall = { dataSource.getPosts() }, context = context)
    }

//        return flow {
//            emit(AuthState.Loading)
//
//            if (!CoreUtility.isInternetConnection(context)) {
//                emit(AuthState.Error(error = "No internet connection"))
//                return@flow
//            }
//
//            val response = dataSource.getPosts()
//
//            if (response.isSuccessful && response.body() != null) {
//                    emit(AuthState.Success(response.body()!!))
//
//            } else {
//                val errorBody = response.errorBody()?.string()
//                val errorMessage = if (errorBody.isNullOrEmpty()) {
//                    response.message() ?: "Error Fetching Data"
//                } else {
//                    try {
//                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
//                        errorResponse.message ?: "Error Fetching Data"
//                    } catch (e: Exception) {
//                        errorBody
//                    }
//                }
//                emit(AuthState.Error(error = errorMessage))
//            }
//        }.catch { e->
//            emit(AuthState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
//
//        }
//    }
}
