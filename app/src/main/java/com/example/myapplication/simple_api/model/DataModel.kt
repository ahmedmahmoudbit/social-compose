package com.example.myapplication.simple_api.model

import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("id")
    val postId: Int,
    val userId: Int,
    val title: String,
    val body: String
)