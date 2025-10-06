package com.example.myapplication.ui.users.presentation.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.users.data.model.UsersResponse
import com.example.myapplication.ui.users.data.repositories.UsersRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersRepoImp,
) : ViewModel() {
    private val _users: MutableStateFlow<LoginState<UsersResponse>> = MutableStateFlow(DataState.Init)
    val users: StateFlow<LoginState<UsersResponse>> = _users

    private val _message: MutableStateFlow<DataState<MessageResponse>> =
        MutableStateFlow(DataState.Init)
    val message: StateFlow<DataState<MessageResponse>> = _message


    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers().collectLatest { response ->
                _users.value = response
            }
        }
    }


}