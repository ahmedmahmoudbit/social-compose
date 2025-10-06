package com.example.myapplication.ui.home.data.model

data class CommentsResponse(
    val totalComments: Int,
    val comments: List<Comment>
)

data class Comment(
    val id: String,
    val text: String,
    val fullName: String,
    val avatar: String,
    val createdAt: String
)