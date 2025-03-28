package com.example.compose.utils.service
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.compose.utils.ApiStrings
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

//    @GET("v2/top-headlines")
//    suspend fun getNews(
//        @Query("country") country: String,
//        @Query("apiKey") apiKey: String = ApiStrings.API_KEY
//    ): Response<NewsResponse>

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
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<RegisterResponse>



}