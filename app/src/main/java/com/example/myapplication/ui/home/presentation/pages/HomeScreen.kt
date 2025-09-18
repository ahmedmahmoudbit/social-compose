package com.example.myapplication.ui.home.presentation.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.AuthState
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.home.data.model.PostResponse
import com.example.myapplication.ui.home.domain.uc.navigationItems
import com.example.myapplication.ui.home.presentation.manager.HomeViewModel
import com.example.myapplication.ui.home.presentation.widgets.CardPost
import com.example.myapplication.utils.components.MyTopAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val postsCollection = viewModel.posts.collectAsState()

//    LaunchedEffect(loginResponse.value) {
//        when (val data = loginResponse.value) {
//            is LoginState.Error -> {
//                Toast.makeText(
//                    context,
//                    data.error,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            LoginState.Init -> {
//                Log.i(TAG, "LoginScreen: Init ");
//            }
//
//            LoginState.Loading -> {
//                Log.i(TAG, "LoginScreen: Loading ");
//            }
//
//            is LoginState.Success -> {
//                CacheHelper(context).setData(CacheString.token,data.data.token)
//                navController.navigate(RouteRegister)
////                Log.i(TAG, "LoginScreen: ${data.data.l} ");
//            }
//        }
//    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = when (selectedTab) {
                    0 -> stringResource(R.string.home_screen)
                    1 -> stringResource(R.string.notification)
                    2 -> stringResource(R.string.users)
                    3 -> stringResource(R.string.profile)
                    else -> stringResource(R.string.home_screen)
                },
                navController = null
            )
        },
        bottomBar = {
            NavigationBar {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
        ) {
            when (selectedTab) {
                0 -> HomeContent(postsCollection = postsCollection.value,)
                1 -> NotificationScreen()
                2 -> UsersScreen()
                3 -> ProfileScreen(navController = navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    postsCollection: LoginState<PostResponse>,
    viewModel: HomeViewModel = hiltViewModel()) {

    val pullRefreshState = rememberPullToRefreshState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (postsCollection) {
            is AuthState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "جاري التحميل...",
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            is AuthState.Success -> {
                val posts = postsCollection.data.posts
                if (posts.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "لا توجد منشورات متاحة",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    PullToRefreshBox(
                        modifier = Modifier.fillMaxSize(),
                        state = pullRefreshState,
                        onRefresh = { viewModel.getPosts() },
                        isRefreshing = postsCollection is AuthState.Loading,
//                        indicatorPadding = 16.dp
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(posts) { post ->
                                CardPost(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(vertical = 12.dp),
                                    post = post
                                )
                            }
                        }
                    }
                }
            }
            is AuthState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = postsCollection.error,
                            modifier = Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.getPosts() },
                            modifier = Modifier
                                .padding(top = 16.dp)
                        ) {
                            Text(text = "إعادة المحاولة")
                        }
                    }
                }
            }
            
            is AuthState.Init -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "جاري التحضير...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}


