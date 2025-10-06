package com.example.myapplication.ui.users.presentation.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.users.data.model.Users
import com.example.myapplication.ui.users.presentation.manager.UsersViewModel
import com.example.myapplication.utils.components.page.LoadingPage
import kotlin.text.ifEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(viewModel: UsersViewModel = hiltViewModel<UsersViewModel>()) {
    val response = viewModel.users.collectAsState()
    val status = response.value
    val refresh =  rememberPullToRefreshState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        when(status) {
            is DataState.Error -> {
                Log.i("UsersScreen", "UsersScreen: ${status.error}")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = status.error,
                            modifier = Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.getUsers() },
                            modifier = Modifier
                                .padding(top = 16.dp)
                        ) {
                            Text(text = "إعادة المحاولة")
                        }
                    }
                }
            }
            is DataState.Success -> {
                val users = status.data.data
                PullToRefreshBox(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp, vertical = 5.dp),
                    state = refresh,
                    onRefresh = { viewModel.getUsers() },
                    isRefreshing = false,
                ) {
                    LazyColumn (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        items(users.size) { index ->
                            UserItem(user = users[index])
//                        if (index < users.size - 1) {
//                            Spacer(modifier = Modifier.height(8.dp))
//                        }
                        }
                    }
                }
            }
            DataState.Init -> {
                LoadingPage()
            }
            DataState.Loading -> {
                LoadingPage()
            }
        }
    }
}

@Composable
private fun UserItem(
    user: Users
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatar.ifEmpty { "https://picsum.photos/500/300" })
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.fullName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = user.username,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
//            Button(
//                onClick = { /* TODO: Handle follow/unfollow */ },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (isFollowing) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
//                ),
//                modifier = Modifier.height(36.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.PersonAdd,
//                    contentDescription = if (isFollowing) "Unfollow" else "Follow",
//                    modifier = Modifier.size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = if (isFollowing) "إلغاء المتابعة" else "متابعة",
//                    fontSize = 12.sp
//                )
//            }
        }
    }
}