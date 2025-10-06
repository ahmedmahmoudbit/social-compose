package com.example.compose.utils.service
import com.example.compose.utils.ApiStrings
import com.example.myapplication.ui.auth.data.models.AddCommentRequest
import com.example.myapplication.ui.auth.data.models.DeletePostRequest
import com.example.myapplication.ui.auth.data.models.ForgetPasswordRequest
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginResponse
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.data.models.VerifyPasswordRequest
import com.example.myapplication.ui.home.data.model.CommentsResponse
import com.example.myapplication.ui.home.data.model.PostResponse
import com.example.myapplication.ui.users.data.model.ProfileResponse
import com.example.myapplication.ui.users.data.model.Users
import com.example.myapplication.ui.users.data.model.UsersResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<PostResponse>

    @GET(ApiStrings.getUsersURL)
    suspend fun getUsers(
    ): Response<UsersResponse>

    @GET(ApiStrings.getProfileURL)
    suspend fun getProfile(
    ): Response<ProfileResponse>

    @FormUrlEncoded
    @PATCH(ApiStrings.changePasswordURL)
    suspend fun changePassword(
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String,
        ): Response<MessageResponse>

    @Multipart
    @PATCH(ApiStrings.updateUserURL)
    suspend fun updateProfile(
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<MessageResponse>

    @DELETE(ApiStrings.deleteAccountURL)
    suspend fun deleteAccount(
    ): Response<MessageResponse>

    @Multipart
    @POST(ApiStrings.AddPostURL)
    suspend fun addPost(
        @Part("title") title: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part img: MultipartBody.Part?
    ): Response<MessageResponse>

//    @DELETE(ApiStrings.deletePostURL)
    @HTTP(method = "DELETE", path = ApiStrings.deletePostURL, hasBody = true)
    suspend fun deletePost(
        @Body postId: DeletePostRequest
    ): Response<MessageResponse>



    @Multipart
    @PUT(ApiStrings.updatePostURL)
    suspend fun updatePost(
        @Path("id") postId: String,
        @Part("title") title: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part img: MultipartBody.Part?

    ): Response<MessageResponse>

    @GET(ApiStrings.getAllCommentsURL)
    suspend fun fetchComments(
        @Query("postId") postId: String,
    ): Response<CommentsResponse>


    @POST(ApiStrings.addCommentURL)
    suspend fun addComments(@Body request: AddCommentRequest):
            Response<MessageResponse>


}