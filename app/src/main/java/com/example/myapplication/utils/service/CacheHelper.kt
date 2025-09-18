package com.example.myapplication.utils.service

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

@Suppress("UNCHECKED_CAST")
@Singleton
class CacheHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun put(key: Preferences.Key<*>, value: Any) {
        context.dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[key as Preferences.Key<String>] = value
                is Int -> preferences[key as Preferences.Key<Int>] = value
                is Boolean -> preferences[key as Preferences.Key<Boolean>] = value
                is Double -> preferences[key as Preferences.Key<Double>] = value
                else -> throw IllegalArgumentException("Unsupported data type")
            }
        }
    }

    fun <T> get(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    suspend fun remove(key: Preferences.Key<*>) {
        context.dataStore.edit { preferences -> preferences.remove(key) }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
