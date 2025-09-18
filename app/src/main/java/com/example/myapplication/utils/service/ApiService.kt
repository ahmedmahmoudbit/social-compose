package com.example.compose.utils.service
import com.example.compose.utils.ApiStrings
import com.example.myapplication.ui.auth.data.models.ForgetPasswordRequest
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.VerifyPasswordRequest
import com.example.myapplication.ui.home.data.model.PostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST(ApiStrings.login_URL)
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>


    @Multipart
    @POST(ApiStrings.register_URL)
    suspend fun register(
        @Part("fullName") fullName: RequestBody,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<MessageResponse>

    @POST(ApiStrings.forgetpassword_URL)
    suspend fun forgetPassword(
        @Body request: ForgetPasswordRequest
    ): Response<MessageResponse>

    @POST(ApiStrings.verify_URL)
    suspend fun verifyPassword(
        @Body request: VerifyPasswordRequest
    ): Response<MessageResponse>

    @GET(ApiStrings.getPosts_URL)
    suspend fun getAllPosts(
    ): Response<PostResponse>


}