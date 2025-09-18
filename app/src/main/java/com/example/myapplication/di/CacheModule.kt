package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.utils.service.CacheHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    
    @Provides
    @Singleton
    fun provideCacheHelper(@ApplicationContext context: Context): CacheHelper {
        return CacheHelper(context)
    }
}