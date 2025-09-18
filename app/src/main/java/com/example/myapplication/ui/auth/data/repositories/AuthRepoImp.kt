package com.example.myapplication.ui.auth.data.repositories
import android.content.Context
import android.util.Log
import com.example.myapplication.simple_api.api.ApiCall
import com.example.myapplication.ui.auth.data.data_sources.AuthDataSource
import com.example.myapplication.ui.auth.data.models.AuthState
import com.example.myapplication.ui.auth.data.models.ForgetPasswordRequest
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.models.RegisterRequest
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.VerifyPasswordRequest
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
          return ApiCall.call(apiCall = { dataSource.login(userData) }, context = context)
//        return flow {
//            emit(AuthState.Loading)
//
//            if (!CoreUtility.isInternetConnection(context)) {
//                emit(AuthState.Error(error = "No internet connection"))
//                return@flow
//            }
//
//            val response = dataSource.login(userData)
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
    }

      fun register(userData: RegisterRequest): Flow<LoginState<MessageResponse>> {
        return flow {
            emit(AuthState.Loading)

            if (!CoreUtility.isInternetConnection(context)) {
                emit(AuthState.Error(error = "No internet connection"))
                return@flow
            }

            try {
                val response = dataSource.register(userData)
                if (response.isSuccessful && response.body() != null) {
                    emit(AuthState.Success(response.body()!!))
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
                    emit(AuthState.Error(error = errorMessage))
                }
            } catch (e: Exception) {
                emit(AuthState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
            }
        }.catch { e->
            Log.e("AUTH", "register: ------ Flow exception: ${e.message}", e)
            emit(AuthState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
        }
    }

    fun forgetPassword(userData: ForgetPasswordRequest): Flow<AuthState<String>> {
        return flow {
            emit(AuthState.Loading)

            if (!CoreUtility.isInternetConnection(context)) {
                emit(AuthState.Error(error = "No internet connection"))
                return@flow
            }

            try {
                val response = dataSource.forgetPassword(userData)
                
                if (response.isSuccessful && response.body() != null) {
                    emit(AuthState.Success("تم إرسال رمز التحقق بنجاح"))
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
                    emit(AuthState.Error(error = errorMessage))
                }
            } catch (e: Exception) {
                emit(AuthState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
            }
        }.catch { e->
            emit(AuthState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
        }
    }

    fun verifyPassword(userData: VerifyPasswordRequest): Flow<AuthState<String>> {
        return flow {
            emit(AuthState.Loading)

            if (!CoreUtility.isInternetConnection(context)) {
                emit(AuthState.Error(error = "No internet connection"))
                return@flow
            }

            try {
                val response = dataSource.verifyPassword(userData)
                
                if (response.isSuccessful && response.body() != null) {
                    emit(AuthState.Success("تم تغيير كلمة المرور بنجاح"))
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
                    emit(AuthState.Error(error = errorMessage))
                }
            } catch (e: Exception) {
                emit(AuthState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
            }
        }.catch { e->
            emit(AuthState.Error(error = e.localizedMessage ?: "Error Fetching Data"))
        }
    }

}
