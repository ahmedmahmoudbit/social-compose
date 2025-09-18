package com.example.myapplication.ui.auth.data.models

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("message") val message: String
)
