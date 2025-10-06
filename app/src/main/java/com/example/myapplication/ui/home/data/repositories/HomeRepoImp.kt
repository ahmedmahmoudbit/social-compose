package com.example.myapplication.ui.home.data.repositories

import android.content.Context
import com.example.myapplication.simple_api.api.ApiCall
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.home.data.data_sources.HomeRepoDataSource
import com.example.myapplication.ui.home.data.model.PostResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepoImp @Inject constructor(
    private val dataSource: HomeRepoDataSource,
    @ApplicationContext private val context: Context
) {
    fun getPosts(
        limit: Int,
        page: Int
    ): Flow<DataState<PostResponse>> {
        return ApiCall.call(apiCall = { dataSource.getPosts(
            limit = limit,
            page = page
        ) }, context = context)
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
