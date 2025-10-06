package com.example.myapplication.ui.users.domain.repositories
import com.example.myapplication.ui.auth.data.models.AddCommentRequest
import com.example.myapplication.ui.auth.data.models.DeletePostRequest
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.PostRequest
import com.example.myapplication.ui.auth.data.models.UpdateRequest
import com.example.myapplication.ui.home.data.model.CommentsResponse
import com.example.myapplication.ui.users.data.model.ProfileResponse
import com.example.myapplication.ui.users.data.model.Users
import com.example.myapplication.ui.users.data.model.UsersResponse
import retrofit2.Response

interface UsersRepo {
    suspend fun getUsers() : Response<UsersResponse>
    suspend fun getProfile() : Response<ProfileResponse>
    suspend fun changePassword(oldPass:String,newPass:String) : Response<MessageResponse>
    suspend fun updateProfile(userData: UpdateRequest) : Response<MessageResponse>
    suspend fun deleteAccount() : Response<MessageResponse>

    suspend fun addPost(post: PostRequest) : Response<MessageResponse>

    suspend fun updatePost(post: PostRequest,id: String) : Response<MessageResponse>
    suspend fun deletePost(post: DeletePostRequest) : Response<MessageResponse>
    suspend fun fetchComments(post:String) : Response<CommentsResponse>
    suspend fun addComments(comment:AddCommentRequest) : Response<MessageResponse>
}