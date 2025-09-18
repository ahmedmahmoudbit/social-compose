package com.example.myapplication.ui.auth.presentation.manager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.auth.data.models.AuthState
import com.example.myapplication.ui.auth.data.models.ForgetPasswordRequest
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.RegisterRequest
import com.example.myapplication.ui.auth.data.models.VerifyPasswordRequest
import com.example.myapplication.ui.auth.data.repositories.AuthRepoImp
import com.example.myapplication.utils.CacheString
import com.example.myapplication.utils.service.CacheHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepoImp,
    private val cacheHelper: CacheHelper
): ViewModel() {

    private val _register : MutableStateFlow<LoginState<MessageResponse>> = MutableStateFlow(AuthState.Init)
    val register : StateFlow<LoginState<MessageResponse>> = _register

    private val _login : MutableStateFlow<LoginState<LoginResponse>> = MutableStateFlow(AuthState.Init)
    val login : StateFlow<LoginState<LoginResponse>> = _login

    private val _forgetStates : MutableStateFlow<AuthState<String>> = MutableStateFlow(AuthState.Init)
    val forgetStates : StateFlow<AuthState<String>> = _forgetStates

    private val _verifyStates : MutableStateFlow<AuthState<String>> = MutableStateFlow(AuthState.Init)
    val verifyStates : StateFlow<AuthState<String>> = _verifyStates



    fun login(userData: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) { repository.login(userData).collectLatest { response ->
            _login.value = response

            if (response is AuthState.Success) {
                cacheHelper.put(CacheString.token, response.data.token)
            }
        }
    }}

    fun register(userData: RegisterRequest) {
        viewModelScope.launch(Dispatchers.IO) { repository.register(userData).collectLatest { response ->
            Log.d(TAG, "register: $response")
            _register.value = response
        } }
    }

    fun forgetPassword(email: String) {
        val request = ForgetPasswordRequest(email = email)
        viewModelScope.launch(Dispatchers.IO) { 
            repository.forgetPassword(request).collectLatest { response ->
                Log.d(TAG, "forgetPassword: $response")
                _forgetStates.value = response
            } 
        }
    }

    fun verifyPassword(email: String, code: String, newPassword: String) {
        val request = VerifyPasswordRequest(
            email = email,
            code = code,
            newPassword = newPassword
        )
        viewModelScope.launch(Dispatchers.IO) { 
            repository.verifyPassword(request).collectLatest { response ->
                Log.d(TAG, "verifyPassword: $response")
                _verifyStates.value = response
            } 
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}