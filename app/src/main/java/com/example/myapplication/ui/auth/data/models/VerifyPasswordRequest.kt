package com.example.myapplication.ui.auth.data.models

data class VerifyPasswordRequest(
    val email: String,
    val code: String,
    val newPassword: String,
)

data class DeletePostRequest(
    val postId: String,
)

data class AddCommentRequest(
    val text: String,
    val postId: String,
)