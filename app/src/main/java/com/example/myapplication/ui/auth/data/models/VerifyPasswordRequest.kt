package com.example.myapplication.ui.auth.data.models

data class VerifyPasswordRequest(
    val email: String,
    val code: String,
    val newPassword: String,
)