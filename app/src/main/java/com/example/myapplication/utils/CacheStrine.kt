package com.example.myapplication.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object CacheString {
    val token = stringPreferencesKey("token")
    val onBoarding = stringPreferencesKey("onBoarding")
}