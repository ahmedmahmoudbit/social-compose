package com.example.myapplication.utils.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.auth.presentation.pages.LoginScreen
import com.example.myapplication.ui.auth.presentation.pages.RegisterScreen
import kotlinx.serialization.Serializable

@Composable
fun AppSerializableHost(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = LoginScreen
    ) {
        composable<LoginScreen>{ LoginScreen(navController) }
        composable<RouteRegister> { RegisterScreen(navController) }
    }
}

@Serializable
object LoginScreen

@Serializable
object RouteRegister

@Serializable
object OTPScreen

@Serializable
data class ScreenHomeTwo(
    val title: String,
    val id: Int
)
