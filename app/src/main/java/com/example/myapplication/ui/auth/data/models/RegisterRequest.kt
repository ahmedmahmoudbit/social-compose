package com.example.myapplication.ui.auth.data.models

import okhttp3.MultipartBody

data class RegisterRequest(
    val fullName: String,
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val avatar: MultipartBody.Part?
)