package com.example.myapplication.simple_api.api

import android.content.Context
import android.util.Log
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.utils.service.model.ErrorResponse
import com.example.utils.CoreUtility
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object ApiCall {
    inline fun <T> call(
        crossinline apiCall: suspend () -> Response<T>,
        context: Context
    ): Flow<DataState<T>> {
        return flow {
            emit(DataState.Loading)

            if (!CoreUtility.isInternetConnection(context)) {
                emit(DataState.Error(error = "No internet connection"))
                return@flow
            }

            try {
                val response = apiCall()
                if (response.isSuccessful && response.body() != null) {
                    emit(DataState.Success(response.body()!!))
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
                    emit(DataState.Error(error = errorMessage))
                }
            } catch (e: Exception) {
                emit(DataState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
            }
        }.catch { e ->
            emit(DataState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
        }
    }

}