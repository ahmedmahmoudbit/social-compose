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
class ProfileViewModel @Inject constructor(
    private val repository: UsersRepoImp,
    private val cacheHelper: CacheHelper
) : ViewModel() {

    private val _users: MutableStateFlow<LoginState<ProfileResponse>> =
        MutableStateFlow(DataState.Init)
    val user: StateFlow<LoginState<ProfileResponse>> = _users

    private val _message: MutableStateFlow<DataState<MessageResponse>> =
        MutableStateFlow(DataState.Init)
    val message: StateFlow<DataState<MessageResponse>> = _message


    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _users.value = DataState.Loading
            repository.getProfile().collectLatest { response ->
                _users.value = response
            }
        }
    }

    fun updateProfile(updateRequest: UpdateRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProfile(updateRequest).collectLatest { response ->
                _message.value = response
                
                // If the update is successful, clear old data and refresh profile data from server
                if (response is DataState.Success) {
                    // Clear current user data to force fresh fetch
                    _users.value = DataState.Loading
                    // Fetch fresh data from server
                    getProfile()
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            cacheHelper.remove(CacheString.token)
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changePassword(
                oldPass = oldPassword,
                newPass = newPassword
            ).collectLatest { response ->
                _message.value = response
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAccount().collectLatest { response ->
                _message.value = response
            }
        }
    }
    
    fun clearMessage() {
        _message.value = DataState.Init
    }
    
    fun refreshProfile() {
        // Clear current data and fetch fresh from server
        _users.value = DataState.Init
        getProfile()
    }

//    fun changePassword(userData: RegisterRequest) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.changePassword(userData).collectLatest { response ->
//                _message.value = response
//            } }
//    }

}