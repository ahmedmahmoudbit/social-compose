package com.example.compose.utils

object ApiStrings {
    const val BASE_URL = "https://node-shop-seven.vercel.app"
    const val login_URL = "$BASE_URL/auth/users/login"
    const val register_URL = "$BASE_URL/auth/users/register"
    const val verify_URL = "$BASE_URL/auth/users/verify-code-and-reset-password"
    const val forgetpassword_URL = "$BASE_URL/auth/users/send-verification-code"
    const val getPosts_URL = "$BASE_URL/posts/get-all-posts"
    const val getUsersURL = "$BASE_URL/auth/users/get_all_users"
    const val changePasswordURL = "$BASE_URL/auth/users/change_password"
    const val updateUserURL = "$BASE_URL/auth/users/update_user"
    const val getProfileURL = "$BASE_URL/auth/users/get_user_data"
    const val deleteAccountURL = "$BASE_URL/auth/users/delete_user"
    const val deletePostURL = "$BASE_URL/posts/delete-post"
    const val updatePostURL = "$BASE_URL/posts/update-post/{id}"
    const val getAllCommentsURL = "$BASE_URL/comments/get-all-comments"
    const val addCommentURL = "$BASE_URL/comments/add-comment"
    const val AddPostURL = "$BASE_URL/posts/add-post"
}