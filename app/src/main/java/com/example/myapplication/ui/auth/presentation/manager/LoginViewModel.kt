package com.example.myapplication.ui.auth.presentation.manager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.auth.data.models.AuthState
import com.example.myapplication.ui.auth.data.models.ForgetPasswordRequest
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.models.RegisterRequest
import com.example.myapplication.ui.auth.data.models.RegisterResponse
import com.example.myapplication.ui.auth.data.models.VerifyPasswordRequest
import com.example.myapplication.ui.auth.data.repositories.AuthRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepoImp
): ViewModel() {

    private val _register : MutableStateFlow<LoginState<RegisterResponse>> = MutableStateFlow(AuthState.Init)
    val register : StateFlow<LoginState<RegisterResponse>> = _register

    private val _login : MutableStateFlow<LoginState<LoginResponse>> = MutableStateFlow(AuthState.Init)
    val login : StateFlow<LoginState<LoginResponse>> = _login

    // Global auth status for forget password and verify password
    private val _authStatus : MutableStateFlow<AuthState<String>> = MutableStateFlow(AuthState.Init)
    val authStatus : StateFlow<AuthState<String>> = _authStatus


     fun login(userData: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) { repository.login(userData).collectLatest { response ->
            _login.value = response
        } }
    }

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
                _authStatus.value = response
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
                _authStatus.value = response
            } 
        }
    }

    fun resetAuthStatus() {
        _authStatus.value = AuthState.Init
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}