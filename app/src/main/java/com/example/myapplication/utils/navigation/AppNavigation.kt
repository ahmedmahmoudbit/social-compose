package com.example.myapplication.utils.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.myapplication.ui.auth.presentation.pages.LoginScreen
import com.example.myapplication.ui.auth.presentation.pages.RegisterScreen
import com.example.myapplication.ui.auth.presentation.pages.RegisterSuccessScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@Composable
fun AppSerializableHost(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = LoginScreen
    ) {
        composable<LoginScreen>{ LoginScreen(navController) }
        composable<RouteRegister> { RegisterScreen(navController) }
//        composable<RouteSuccessScreen> { RegisterSuccessScreen(navController) }
        composable<RouteSuccessScreen> { it ->
            RegisterSuccessScreen(navController, it)
        }

    }
}

@Serializable
object LoginScreen

@Serializable
object RouteRegister

@Serializable
object OTPScreen

@Serializable
data class RouteSuccessScreen(
    val title: String,
)
