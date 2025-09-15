package com.example.myapplication.simple_api.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.simple_api.api.RetrofitSingleton
import com.example.myapplication.simple_api.model.DataModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class PostViewModel: ViewModel() {
    private  val _posts= mutableStateOf<List<DataModel>>(emptyList())
    val posts: State<List<DataModel>> = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val fetchedPosts = RetrofitSingleton.api.getData()
                _posts.value = fetchedPosts
            } catch (e: Exception) {
                Log.i("PostViewModel", "fetchPosts: ${e.localizedMessage}" )
            }
        }

    }

}