package com.example.myapplication.utils.navigation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.auth.presentation.pages.LoginScreen
import com.example.myapplication.ui.auth.presentation.pages.RegisterScreen
import com.example.myapplication.ui.auth.presentation.pages.RegisterSuccessScreen
import com.example.myapplication.ui.auth.presentation.pages.ResetPasswordScreen
import com.example.myapplication.ui.home.presentation.pages.HomeScreen
import com.example.myapplication.ui.onboarding.OnboardingScreen
import com.example.myapplication.utils.CacheString
import com.example.myapplication.utils.service.CacheHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.Serializable
import javax.inject.Inject
import kotlin.reflect.KClass


@HiltViewModel
class AppViewModel @Inject constructor(private val cacheHelper: CacheHelper) : ViewModel() {
    val startDestination: StateFlow<KClass<out Any>?> = cacheHelper.get(CacheString.onBoarding, "")
        .combine(cacheHelper.get(CacheString.token, "")) { onBoarding, token ->
            when {
                onBoarding.isEmpty() -> OnBoardingRoute::class
                token.isEmpty() -> LoginRoute::class
                else -> HomeScreenRoute::class
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
}

@Composable
fun AppSerializableHost(navController: NavHostController,viewModel: AppViewModel = hiltViewModel()) {
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

    if (startDestination == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    NavHost(
        navController = navController, startDestination = startDestination!!
    ) {
        composable<OnBoardingRoute>{ OnboardingScreen(navController) }
        composable<LoginRoute>{ LoginScreen(navController) }
        composable<ForgotPasswordRoute>{ ResetPasswordScreen(navController) }
        composable<RouteRegister> { RegisterScreen(navController) }
        composable<HomeScreenRoute> { HomeScreen(navController) }
        composable<RouteSuccessScreen> { it ->
            RegisterSuccessScreen(navController, it)
        }

    }
}

@Serializable
object HomeScreenRoute

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
