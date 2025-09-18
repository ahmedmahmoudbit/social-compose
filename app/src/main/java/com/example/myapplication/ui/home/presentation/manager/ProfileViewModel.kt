package com.example.myapplication.ui.home.presentation.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.auth.data.models.AuthState
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.repositories.AuthRepoImp
import com.example.myapplication.ui.home.data.model.PostResponse
import com.example.myapplication.ui.home.data.repositories.HomeRepoImp
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
class ProfileViewModel @Inject constructor(
    private val cacheHelper: CacheHelper
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            cacheHelper.remove(CacheString.token)
        }
    }

}