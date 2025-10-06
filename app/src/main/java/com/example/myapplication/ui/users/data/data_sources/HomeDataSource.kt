package com.example.myapplication.ui.users.data.data_sources

import com.example.compose.utils.service.ApiService
import com.example.myapplication.ui.auth.data.models.AddCommentRequest
import com.example.myapplication.ui.auth.data.models.DeletePostRequest
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.PostRequest
import com.example.myapplication.ui.auth.data.models.UpdateRequest
import com.example.myapplication.ui.home.data.model.CommentsResponse
import com.example.myapplication.ui.home.data.model.PostResponse
import com.example.myapplication.ui.home.domain.repositories.HomeRepo
import com.example.myapplication.ui.users.data.model.ProfileResponse
import com.example.myapplication.ui.users.data.model.Users
import com.example.myapplication.ui.users.data.model.UsersResponse
import com.example.myapplication.ui.users.domain.repositories.UsersRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class UsersRepoDataSource @Inject constructor(
    private val apiService: ApiService
) : UsersRepo {

    override suspend fun getUsers(): Response<UsersResponse> {
        return apiService.getUsers()
    }

    override suspend fun getProfile(): Response<ProfileResponse> {
        return apiService.getProfile()
    }

    override suspend fun fetchComments(postId:String): Response<CommentsResponse> {
        return apiService.fetchComments(postId)
    }

    override suspend fun addComments(comment:AddCommentRequest): Response<MessageResponse> {
        return apiService.addComments(comment)
    }

    override suspend fun changePassword(
        oldPass: String,
        newPass: String
    ): Response<MessageResponse> {
        return apiService.changePassword(
            oldPassword = oldPass,
            newPassword = newPass,
        )
    }

    override suspend fun updateProfile(userData: UpdateRequest): Response<MessageResponse> {
        return try {
            val (requestBodyMap, avatarPart) = prepareUpdateProfileRequest(userData)
            val response = apiService.updateProfile(
                fullName = requestBodyMap["fullName"]!!,
                email = requestBodyMap["email"]!!,
                phone = requestBodyMap["phone"]!!,
                avatar = avatarPart
            )
            response
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteAccount(): Response<MessageResponse> {
        return apiService.deleteAccount()
    }

    override suspend fun addPost(post: PostRequest): Response<MessageResponse> {
        val requestBodyMap = mutableMapOf<String, RequestBody>()
        requestBodyMap["title"] = post.title.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["desc"] = post.desc.toRequestBody("text/plain".toMediaTypeOrNull())
        val avatarPart = post.img
        val (data, image) = Pair(requestBodyMap, avatarPart)
        return apiService.addPost(
            title = data["title"]!!,
            desc = data["desc"]!!,
            img = image
        )
    }

    override suspend fun deletePost(post: DeletePostRequest): Response<MessageResponse> {
        return apiService.deletePost(post)
    }

    override suspend fun updatePost(post: PostRequest, id: String): Response<MessageResponse> {
        val requestBodyMap = mutableMapOf<String, RequestBody>()
        requestBodyMap["title"] = post.title.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["desc"] = post.desc.toRequestBody("text/plain".toMediaTypeOrNull())
        val avatarPart = post.img
        val (data, image) = Pair(requestBodyMap, avatarPart)
        return apiService.updatePost(
            postId = id,
            title = data["title"]!!,
            desc = data["desc"]!!,
            img = image
        )
    }

}

fun prepareUpdateProfileRequest(updateRequest: UpdateRequest): Pair<Map<String, RequestBody>, MultipartBody.Part?> {
    val requestBodyMap = mutableMapOf<String, RequestBody>()

    if (updateRequest.fullName.isBlank()) {
        throw IllegalArgumentException("الاسم الكامل مطلوب")
    }
    if (updateRequest.email.isBlank()) {
        throw IllegalArgumentException("البريد الإلكتروني مطلوب")
    }
    if (updateRequest.phone.isBlank()) {
        throw IllegalArgumentException("رقم الهاتف مطلوب")
    }

    requestBodyMap["fullName"] = updateRequest.fullName.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["email"] = updateRequest.email.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["phone"] = updateRequest.phone.toRequestBody("text/plain".toMediaTypeOrNull())
    val avatarPart = updateRequest.avatar
    return Pair(requestBodyMap, avatarPart)
}


