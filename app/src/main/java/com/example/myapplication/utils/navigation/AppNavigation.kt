package com.example.myapplication.utils.navigation
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.myapplication.ui.home.presentation.pages.ChangePasswordScreen
import com.example.myapplication.ui.home.presentation.pages.AddPostScreen
import com.example.myapplication.ui.home.presentation.pages.HomeScreen
import com.example.myapplication.ui.onboarding.OnboardingScreen
import com.example.myapplication.ui.settings.presentation.pages.ThemeSettingsScreen
import com.example.myapplication.ui.users.data.model.Users
import com.example.myapplication.ui.users.presentation.pages.UpdateProfileScreen
import com.example.myapplication.utils.CacheString
import com.example.myapplication.utils.navigation.NavigationHelper
import com.example.myapplication.utils.resource.Utils
import com.example.myapplication.utils.service.CacheHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject
import kotlin.reflect.KClass


@HiltViewModel
class AppViewModel @Inject constructor(private val cacheHelper: CacheHelper) : ViewModel() {

    private val _currentTheme = MutableStateFlow("SYSTEM")
    val currentTheme: StateFlow<String> = _currentTheme

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val savedUsername = cacheHelper.get(CacheString.username, "").first()
            Utils.username = savedUsername
            
            val savedTheme = cacheHelper.get(CacheString.theme, "SYSTEM").first()
            _currentTheme.value = savedTheme
            Utils.currentTheme = savedTheme
        }
        
        // Listen for theme changes
        viewModelScope.launch {
            cacheHelper.get(CacheString.theme, "SYSTEM").collect { theme ->
                _currentTheme.value = theme
            }
        }
    }
    
    fun updateTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cacheHelper.put(CacheString.theme, theme)
            _currentTheme.value = theme
        }
    }
    
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

@RequiresApi(Build.VERSION_CODES.O)
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
        composable<ChangePasswordRoute>{ ChangePasswordScreen(navController) }
        composable<ChangeProfileRoute> { backStackEntry ->
            UpdateProfileScreen(navController, backStackEntry)
        }
        composable<ThemeSettingsRoute> {
            ThemeSettingsScreen(navController)
        }
        composable<ForgotPasswordRoute>{ ResetPasswordScreen(navController) }
        composable<RouteRegister> { RegisterScreen(navController) }
        composable<HomeScreenRoute> { HomeScreen(navController) }
        composable<RouteSuccessScreen> { it ->
            RegisterSuccessScreen(navController, it)
        }
        composable<AddPostRoute> { 
            AddPostScreen(
                navController = navController,
                showBackButton = true  // Show back button when navigated directly
            )
        }
        composable<EditPostRoute> { backStackEntry ->
            val selectedPost = NavigationHelper.selectedPostForEdit
            if (selectedPost != null) {
                AddPostScreen(
                    navController = navController,
                    isEdit = true,
                    postId = selectedPost.postId,
                    initialTitle = selectedPost.title,
                    initialDesc = selectedPost.desc,
                    initialImageUrl = selectedPost.img,
                    showBackButton = true  // Always show back button for editing
                )
            } else {
                // Fallback - navigate back if no post data
                navController.popBackStack()
            }
        }

    }
}

@Serializable
data class ChangeProfileRoute(
    val data: String
)

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
object ChangePasswordRoute

@Serializable
object ThemeSettingsRoute

@Serializable
object AddPostRoute

@Serializable
data class EditPostRoute(
    val postId: String,
)


@Serializable
data class RouteSuccessScreen(
    val title: String,
)


