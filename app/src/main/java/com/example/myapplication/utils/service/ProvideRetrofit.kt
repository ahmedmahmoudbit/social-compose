package com.example.myapplication.utils.service

import android.content.Context
import com.example.myapplication.ui.auth.data.repositories.AuthRepoImp
import com.example.myapplication.ui.auth.domain.repositories.AuthRepo
import com.example.compose.utils.ApiStrings
import com.example.compose.utils.service.ApiService
import com.example.myapplication.ui.auth.data.data_sources.AuthDataSource
import com.example.myapplication.utils.CacheString
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvideRetrofit {

    @Provides
    @Singleton
    fun provideRetrofit(
        cacheHelper: CacheHelper
    ): Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val token = runBlocking {
                    cacheHelper.get(CacheString.token, "").first()
                }

                val newRequest = chain.request().newBuilder().apply {
                    if (token.isNotEmpty()) {
                        addHeader("Authorization", "Bearer $token")
                    }
                    addHeader("Content-Type", "application/json")
                    addHeader("Accept-Language", "en")
                }.build()

                chain.proceed(newRequest)
            }
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiStrings.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesRemoteDataSource(apiService: ApiService): AuthRepo = AuthDataSource(apiService)

    @Provides
    @Singleton
    fun providesNewsRepository(
        dataSource: AuthDataSource,
        @ApplicationContext context: Context
    ): AuthRepoImp {
        return AuthRepoImp(dataSource, context)
    }

}