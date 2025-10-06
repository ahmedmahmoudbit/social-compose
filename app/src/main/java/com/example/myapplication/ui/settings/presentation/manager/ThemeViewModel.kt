package com.example.myapplication.ui.settings.presentation.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.utils.CacheString
import com.example.myapplication.utils.service.CacheHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val cacheHelper: CacheHelper
) : ViewModel() {

    private val _currentTheme = MutableStateFlow("SYSTEM")
    val currentTheme: StateFlow<String> = _currentTheme

    init {
        loadSavedTheme()
        // Listen for theme changes from cache
        viewModelScope.launch {
            cacheHelper.get(CacheString.theme, "SYSTEM").collect { theme ->
                _currentTheme.value = theme
            }
        }
    }

    private fun loadSavedTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            val savedTheme = cacheHelper.get(CacheString.theme, "SYSTEM").first()
            _currentTheme.value = savedTheme
        }
    }

    fun setTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cacheHelper.put(CacheString.theme, theme)
            _currentTheme.value = theme
        }
    }
    
    fun getCurrentTheme(): String = _currentTheme.value
}