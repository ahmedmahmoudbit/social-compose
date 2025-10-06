package com.example.myapplication.ui.home.presentation.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.home.data.model.PostResponse
import com.example.myapplication.ui.home.domain.uc.navigationItems
import com.example.myapplication.ui.home.presentation.manager.HomeViewModel
import com.example.myapplication.ui.home.presentation.widgets.CardPost
import com.example.myapplication.ui.users.presentation.pages.UsersScreen
import com.example.myapplication.utils.components.MyTopAppBar
import com.example.myapplication.utils.components.colors.cardColorBackground
import verticalSpace

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

    LaunchedEffect(selectedTab) {
        if (selectedTab == 0) { // Home tab
            viewModel.forceRefreshFromServer()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = when (selectedTab) {
                    0 -> stringResource(R.string.home_screen)
                    1 -> stringResource(R.string.add_post)
                    2 -> stringResource(R.string.users)
                    3 -> stringResource(R.string.profile)
                    else -> stringResource(R.string.home_screen)
                },
                navController = null
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.cardColorBackground,
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.title)
                            )
                        },
                        label = { Text(stringResource(item.title)) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },

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
                0 -> HomeContent(postsCollection = postsCollection.value, navController = navController)
                1 -> AddPostScreen(showBackButton = false)
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
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()) {
    val pullRefreshState = rememberPullToRefreshState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val listState = rememberLazyListState()

    // كشف تلقائي عن نهاية القائمة
    LaunchedEffect(listState) {
        snapshotFlow { 
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index 
        }.collect { lastVisibleIndex ->
            if (lastVisibleIndex != null) {
                val totalItems = listState.layoutInfo.totalItemsCount
                // عندما نصل للعنصر قبل الأخير
                if (lastVisibleIndex >= totalItems - 2 && totalItems > 0) {
                    viewModel.loadMorePosts()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (postsCollection) {
            is DataState.Loading -> {
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
            is DataState.Success -> {
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
                        onRefresh = { viewModel.refreshData() },
                        isRefreshing = false,
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(posts) { post ->
                                CardPost(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(vertical = 12.dp),
                                    post = post,
                                    navController = navController
                                )
                            }
                            
                            // عنصر Loading في النهاية
                            item {
                                if (isLoadingMore) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(40.dp),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                            
                            item {
                                10.verticalSpace()
                            }
                        }
                    }
                }
            }
            is DataState.Error -> {
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
            is DataState.Init -> {
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


