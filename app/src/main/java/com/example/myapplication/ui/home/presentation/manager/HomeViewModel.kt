package com.example.myapplication.ui.home.presentation.manager

import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.repositories.AuthRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepoImp
): ViewModel() {
    private val _posts : MutableStateFlow<LoginState<LoginResponse>> = MutableStateFlow(LoginState.Init)
    val posts : StateFlow<LoginState<LoginResponse>> = _posts

//    init {
//        login()
//    }

//     fun login(userData: LoginRequest) {
//        viewModelScope.launch(Dispatchers.IO) { repository.login(userData).collectLatest { response ->
//            _login.value = response
//        } }
//    }
//
//    fun register(userData: RegisterRequest) {
//        viewModelScope.launch(Dispatchers.IO) { repository.register(userData).collectLatest { response ->
//            _register.value = response
//        } }
//    }
//

}