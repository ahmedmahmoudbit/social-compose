package com.example.myapplication.ui.home.data.data_sources

import com.example.compose.utils.service.ApiService
import com.example.myapplication.ui.home.data.model.PostResponse
import com.example.myapplication.ui.home.domain.repositories.HomeRepo
import retrofit2.Response
import javax.inject.Inject

class HomeRepoDataSource @Inject constructor(
    private val apiService: ApiService
) : HomeRepo {

    override suspend fun getPosts(
        limit: Int,
        page: Int
    ): Response<PostResponse> {
        return apiService.getAllPosts(
            limit = limit,
            page = page
        )
    }

}


