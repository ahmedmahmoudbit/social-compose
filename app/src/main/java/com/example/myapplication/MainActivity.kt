package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.simple_api.ui.PostsScreen
import com.example.myapplication.utils.navigation.AppSerializableHost
import com.example.myapplication.utils.navigation.AppViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appViewModel: AppViewModel = hiltViewModel()
            val currentTheme by appViewModel.currentTheme.collectAsState()
            val isSystemInDarkTheme = isSystemInDarkTheme()
            
            val darkTheme = when (currentTheme) {
                "LIGHT" -> false
                "DARK" -> true
                "SYSTEM" -> isSystemInDarkTheme
                else -> isSystemInDarkTheme
            }
            
            MyApplicationTheme(darkTheme = darkTheme) {
                AppSerializableHost(
                    navController = rememberNavController(),
                )
            }
        }
    }
}