package com.example.myapplication.ui.home.presentation.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.UpdateRequest
import com.example.myapplication.ui.users.data.model.ProfileResponse
import com.example.myapplication.ui.users.data.repositories.UsersRepoImp
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
class PostsViewModel @Inject constructor(
    private val repository: UsersRepoImp,
    private val cacheHelper: CacheHelper
) : ViewModel() {

    private val _users: MutableStateFlow<LoginState<ProfileResponse>> =
        MutableStateFlow(DataState.Init)
    val user: StateFlow<LoginState<ProfileResponse>> = _users

    private val _message: MutableStateFlow<DataState<MessageResponse>> =
        MutableStateFlow(DataState.Init)
    val message: StateFlow<DataState<MessageResponse>> = _message



}