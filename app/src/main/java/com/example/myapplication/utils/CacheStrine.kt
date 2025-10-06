package com.example.myapplication.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object CacheString {
    val token = stringPreferencesKey("token")
    val username = stringPreferencesKey("username")
    val onBoarding = stringPreferencesKey("onBoarding")
    val theme = stringPreferencesKey("theme")
}