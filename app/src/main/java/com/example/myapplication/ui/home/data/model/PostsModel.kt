package com.example.myapplication.ui.home.data.model

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val fullName: String,
    val username: String,
    val avatar: String,
    val userId: String? = null
)

@Serializable
data class Post(
    @Json(name = "_id")
    val postId: String,
    val user: User,
    val title: String,
    val desc: String,
    val isLiked: Boolean,
    val isFavorite: Boolean,
    val img: String,
    val createdAt: String
)

@Serializable
data class PostResponse(
    val totalPosts: Int,
    val totalCurrent: Int,
    val posts: List<Post>
)