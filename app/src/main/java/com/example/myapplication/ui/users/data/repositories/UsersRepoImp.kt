package com.example.myapplication.ui.users.data.repositories

import android.content.Context
import com.example.myapplication.simple_api.api.ApiCall
import com.example.myapplication.ui.auth.data.models.AddCommentRequest
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.DeletePostRequest
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.PostRequest
import com.example.myapplication.ui.auth.data.models.UpdateRequest
import com.example.myapplication.ui.home.data.model.CommentsResponse
import com.example.myapplication.ui.users.data.data_sources.UsersRepoDataSource
import com.example.myapplication.ui.users.data.model.ProfileResponse
import com.example.myapplication.ui.users.data.model.UsersResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepoImp @Inject constructor(
    private val dataSource: UsersRepoDataSource,
    @ApplicationContext private val context: Context
) {
    fun getUsers(): Flow<DataState<UsersResponse>> {
        return ApiCall.call(apiCall = { dataSource.getUsers() }, context = context)
    }

    fun getProfile(): Flow<DataState<ProfileResponse>> {
        return ApiCall.call(apiCall = { dataSource.getProfile() }, context = context)
    }

    fun fetchComments(postId: String): Flow<DataState<CommentsResponse>> {
        return ApiCall.call(apiCall = { dataSource.fetchComments(postId) }, context = context)
    }

    fun addComments(request: AddCommentRequest): Flow<DataState<MessageResponse>> {
        return ApiCall.call(apiCall = { dataSource.addComments(request) }, context = context)
    }


    fun changePassword(oldPass: String, newPass: String): Flow<DataState<MessageResponse>> {
        return ApiCall.call(apiCall = {
            dataSource.changePassword(
                oldPass = oldPass,
                newPass = newPass,
            )
        }, context = context)
    }

    fun updateProfile(userData: UpdateRequest): Flow<DataState<MessageResponse>> {
        return ApiCall.call(apiCall = { dataSource.updateProfile(userData) }, context = context)
    }

    fun deleteAccount(): Flow<DataState<MessageResponse>> {
        return ApiCall.call(apiCall = { dataSource.deleteAccount() }, context = context)
    }

    fun addPost(data: PostRequest): Flow<DataState<MessageResponse>> {
        return ApiCall.call(apiCall = { dataSource.addPost(data) }, context = context)
    }

    fun deletePost(data: DeletePostRequest): Flow<DataState<MessageResponse>> {
        return ApiCall.call(apiCall = { dataSource.deletePost(data) }, context = context)
    }

    fun updatePost(data: PostRequest, id: String): Flow<DataState<MessageResponse>> {
        return ApiCall.call(apiCall = { dataSource.updatePost(data, id) }, context = context)
    }
}
