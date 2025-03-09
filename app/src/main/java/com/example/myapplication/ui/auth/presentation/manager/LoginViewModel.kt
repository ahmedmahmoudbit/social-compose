package com.example.myapplication.ui.auth.presentation.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.clean_art.retrofit.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginState
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

    private val _login : MutableStateFlow<LoginState<LoginResponse>> = MutableStateFlow(LoginState.Init)
    val login : StateFlow<LoginState<LoginResponse>> = _login

//    init {
//        login()
//    }

     fun login(userData: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) { repository.login(userData).collectLatest { response ->
            _login.value = response
        } }
    }

    fun register(userData: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) { repository.login(userData).collectLatest { response ->
            _login.value = response
        } }
    }

    companion object {
        private const val TAG = "NewsViewModel"
    }

}