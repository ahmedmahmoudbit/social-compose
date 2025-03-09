package com.example.compose.clean_art.retrofit.data.models
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: LoginData
)

data class LoginData(
    @SerializedName("user") val user: User,
    @SerializedName("isSubscribe") val isSubscribe: Boolean,
    @SerializedName("currentPlan") val currentPlan: String,
    @SerializedName("shops") val shops: List<Shop>,
    @SerializedName("shopCategory") val shopCategory: List<ShopCategory>,
    @SerializedName("access") val access: Access
)

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("isVerified") val isVerified: Boolean,
    @SerializedName("phone") val phone: String,
    @SerializedName("company_name") val companyName: String?,
    @SerializedName("profile_photo") val profilePhoto: String = "",
    @SerializedName("role") val role: String
)

data class Shop(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("shop_category_id") val shopCategoryId: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("is_lifetime") val isLifetime: Int?,
    @SerializedName("deleted_at") val deletedAt: String?
)

data class ShopCategory(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("primary_color") val primaryColor: String?,
    @SerializedName("secondary_color") val secondaryColor: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("status") val status: String?,
)

data class Access(
    @SerializedName("auth_type") val authType: String?,
    @SerializedName("token") val token: String,
    @SerializedName("expires_at") val expiresAt: String?
)