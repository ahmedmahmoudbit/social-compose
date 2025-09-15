package com.example.compose.utils

object ApiStrings {
    const val BASE_URL = "https://node-shop-seven.vercel.app"
    const val login_URL = "$BASE_URL/auth/users/login"
    const val register_URL = "$BASE_URL/auth/users/register"
    const val verify_URL = "$BASE_URL/auth/users/verify-code-and-reset-password"
    const val forgetpassword_URL = "$BASE_URL/auth/users/send-verification-code"

    const val getPosts_URL = "$BASE_URL/posts/get-all-posts"
    const val Language = "ar"
}