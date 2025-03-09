package com.example.compose.utils.service
import com.example.compose.clean_art.retrofit.data.models.LoginResponse
import com.example.compose.utils.ApiStrings
import com.example.myapplication.ui.auth.data.models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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

}