package com.example.myapplication.ui.home.presentation.manager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.auth.data.models.AddCommentRequest
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.DeletePostRequest
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.PostRequest
import com.example.myapplication.ui.home.data.model.CommentsResponse
import com.example.myapplication.ui.home.data.model.PostResponse
import com.example.myapplication.ui.home.data.repositories.HomeRepoImp
import com.example.myapplication.ui.users.data.repositories.UsersRepoImp
import com.example.myapplication.utils.service.CacheHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepoImp,
    private val repository2: UsersRepoImp,
    private val cacheHelper: CacheHelper
) : ViewModel() {
    private val _posts: MutableStateFlow<LoginState<PostResponse>> =
        MutableStateFlow(DataState.Init)
    val posts: StateFlow<LoginState<PostResponse>> = _posts

    private val _comments: MutableStateFlow<LoginState<CommentsResponse>> =
        MutableStateFlow(DataState.Init)
    val comments: StateFlow<LoginState<CommentsResponse>> = _comments


    private val _message: MutableStateFlow<LoginState<MessageResponse>> =
        MutableStateFlow(DataState.Init)
    val message: StateFlow<LoginState<MessageResponse>> = _message

    // Pagination variables
    private var currentPage = 1
    private val pageSize = 3
    private var isLastPage = false
//    private var isLoadingMore = false

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    private var allPosts = mutableListOf<com.example.myapplication.ui.home.data.model.Post>()


    init {
        getPosts(
            limit = pageSize,
            page = 1,
            isRefresh = true
        )
    }

    fun getPosts(
        limit: Int = pageSize,
        page: Int = currentPage,
        isRefresh: Boolean = false
    ) {
        // منع التحميل المتعدد
        if (isLoadingMore.value && !isRefresh) return
        
        // إذا وصلنا لآخر صفحة
        if (isLastPage && !isRefresh) return
        
        viewModelScope.launch(Dispatchers.IO) {
            // إذا كان refresh، نعيد تعيين كل شيء
            if (isRefresh) {
                currentPage = 1
                isLastPage = false
                allPosts.clear()
                _posts.value = DataState.Loading
            } else {
//                isLoadingMore = true
                _isLoadingMore.value = true
            }
            
            repository.getPosts(
                limit = limit,
                page = page
            ).collectLatest { response ->
                when (response) {
                    is DataState.Success -> {
                        val newPosts = response.data.posts
                        
                        // إضافة المنشورات الجديدة للقائمة التراكمية
                        allPosts.addAll(newPosts)
                        
                        // التحقق من وصولنا لآخر صفحة
                        isLastPage = allPosts.size >= response.data.totalPosts
                        
                        // تحديث الحالة بكل المنشورات
                        val updatedResponse = response.data.copy(posts = allPosts.toList())
                        _posts.value = DataState.Success(updatedResponse)
                        
                        // زيادة رقم الصفحة للتحميل القادم
                        currentPage++
                    }
                    is DataState.Error -> {
                        if (isRefresh) {
                            _posts.value = response
                        }
                        // في حالة الخطأ أثناء التحميل الإضافي، نبقي على البيانات الحالية
                    }
                    is DataState.Loading -> {
                        if (isRefresh) {
                            _posts.value = DataState.Loading
                        }
                    }
                    else -> {}
                }
                
//                isLoadingMore = false
                _isLoadingMore.value = false
            }
        }
    }

    fun loadMorePosts() {
        if (!isLoadingMore.value && !isLastPage) {
            getPosts(page = currentPage, isRefresh = false)
        }
    }

    fun fetchComments(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("HomeViewModel", "Fetching comments for post: $postId")
            _comments.value = DataState.Loading  // Fixed: was updating _posts
            repository2.fetchComments(postId).collectLatest { response ->
                Log.d("HomeViewModel", "Comments response: $response")
                _comments.value = response
            }
        }
    }

    fun addComment(request: AddCommentRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _message.value = DataState.Loading  // Fixed: was updating _posts
            repository2.addComments(request).collectLatest { response ->
                _message.value = response

                if (response is DataState.Success) {
                    fetchComments(request.postId)
                    clearMessageState()
                }
            }
        }
    }

    fun addPost(data: PostRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository2.addPost(data).collectLatest { response ->
                _message.value = response

                if (response is DataState.Success) {
                    // Force refresh from server to get the latest data
                    forceRefreshFromServer()
                }
            }
        }
    }

    fun deletePost(data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val postRequest = DeletePostRequest(postId = data)
            repository2.deletePost(postRequest).collectLatest { response ->
                _message.value = response

                if (response is DataState.Success) {
                    // تحديث القائمة المحلية
                    allPosts.removeAll { it.postId == data }
                    
                    val currentState = _posts.value
                    if (currentState is DataState.Success) {
                        val updatedPosts = allPosts.toList()
                        val newResponse = currentState.data.copy(
                            posts = updatedPosts,
                            totalPosts = currentState.data.totalPosts - 1,
                            totalCurrent = updatedPosts.size
                        )
                        _posts.value = DataState.Success(newResponse)
                    }
                    clearMessageState()
                }

            }
        }
    }

    fun updatePost(postId: String, data: PostRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository2.updatePost(data, postId).collectLatest { response ->
                _message.value = response

                if (response is DataState.Success) {
                    forceRefreshFromServer()
                }
            }
        }
    }

    fun clearMessageState() {
        _message.value = DataState.Init
    }

    fun clearCommentsState() {
        _comments.value = DataState.Init
    }

    fun refreshData() {
        currentPage = 1
        isLastPage = false
        allPosts.clear()
        getPosts(
            limit = pageSize,
            page = 1,
            isRefresh = true
        )
    }

    fun forceRefreshFromServer() {
        currentPage = 1
        isLastPage = false
        allPosts.clear()
        getPosts(
            limit = pageSize,
            page = 1,
            isRefresh = true
        )
    }

    fun clearAllStates() {
        currentPage = 1
        isLastPage = false
        allPosts.clear()
        _posts.value = DataState.Init
        _message.value = DataState.Init
    }

}