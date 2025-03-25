package com.example.myapplication.ui.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val token: String,
    val data: UserData
)

@Serializable
data class UserData(
    val fullName: String,
    val username: String,
    val email: String,
    val phone: Long,
    val isEmployee: Boolean,
    val isEmailVerify: Boolean,
    val isAdmin: Boolean,
    val avatar: String,
    val createdAt: String,
    val updatedAt: String,
    val id: String
)