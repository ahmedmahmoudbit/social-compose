package com.example.myapplication.ui.users.data.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable


data class UsersResponse(
    val data: List<Users>
)

data class ProfileResponse(
    val data: Users
)

@Serializable
data class Users(
    val _id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val isEmailVerify: Boolean,
    val isEmployee: Boolean,
    val isAdmin: Boolean,
    val avatar: String,
    val createdAt: String,
    val updatedAt: String,
    val phone: String?,
)