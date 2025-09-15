package com.example.myapplication.utils.navigation
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
import com.example.myapplication.ui.auth.presentation.pages.LoginScreen
import com.example.myapplication.ui.auth.presentation.pages.RegisterScreen
import com.example.myapplication.ui.auth.presentation.pages.RegisterSuccessScreen
import com.example.myapplication.ui.auth.presentation.pages.ResetPasswordScreen
import com.example.myapplication.ui.home.presentation.pages.HomeScreen
import com.example.myapplication.ui.onboarding.OnboardingScreen
import kotlinx.serialization.Serializable

@Composable
fun AppSerializableHost(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = LoginRoute
    ) {
        composable<OnBoardingRoute>{ OnboardingScreen(navController) }
        composable<LoginRoute>{ LoginScreen(navController) }
        composable<ForgotPasswordRoute>{ ResetPasswordScreen(navController) }
        composable<RouteRegister> {
            RegisterScreen(navController) }
        composable<HomeScreen> { HomeScreen(navController) }
//        composable<RouteSuccessScreen> { RegisterSuccessScreen(navController) }
        composable<RouteSuccessScreen> { it ->
            RegisterSuccessScreen(navController, it)
        }

    }
}

@Serializable
object HomeScreen

@Serializable
object LoginRoute

@Serializable
object OnBoardingRoute

@Serializable
object RouteRegister

@Serializable
object ForgotPasswordRoute

@Serializable
object OTPScreen

@Serializable
data class RouteSuccessScreen(
    val title: String,
)
