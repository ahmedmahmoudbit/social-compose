package com.example.myapplication.ui.home.presentation.widgets

import android.R.attr.data
import android.R.id.message
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.internal.NavContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.AddCommentRequest
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.home.data.model.Comment
import com.example.myapplication.ui.home.data.model.CommentsResponse
import com.example.myapplication.ui.home.data.model.Post
import com.example.myapplication.ui.home.data.model.User
import com.example.myapplication.ui.home.presentation.manager.HomeViewModel
import com.example.myapplication.ui.home.presentation.widgets.comment.CommentsBottomSheet
import com.example.myapplication.utils.components.LoadingDialog
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.components.colors.cardColorBackground
import com.example.myapplication.utils.components.page.LoadingPage
import com.example.myapplication.utils.navigation.EditPostRoute
import com.example.myapplication.utils.navigation.NavigationHelper
import com.example.myapplication.utils.navigation.RouteRegister
import com.example.myapplication.utils.resource.Utils
import com.example.myapplication.utils.resource.Utils.formatTimeAgo
import horizontalSpace

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardPost(
    modifier: Modifier = Modifier,
    post: Post? = null,
    navController: NavHostController,
    viewmodel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    onComment: () -> Unit = { }
) {
    val response = viewmodel.message.collectAsState()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val isSheetOpen = remember { mutableStateOf(false) }
    if (response.value is DataState.Loading) {
        LoadingDialog(isVisible = true)
    }
    LaunchedEffect(response.value) {
        when (response.value) {
            DataState.Init -> {}
            DataState.Loading -> {}
            is DataState.Error -> {}
            is DataState.Success -> {
                Toast.makeText(
                    context,
                    (response.value as DataState.Success<MessageResponse>).data.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    val displayPost = post ?: Post(
        postId = "1",
        user = User(
            fullName = "مستخدم تجريبي",
            username = "test_user",
            avatar = "",
            userId = "1"
        ),
        title = "منشور تجريبي",
        desc = "هذا منشور تجريبي للعرض",
        isLiked = false,
        isFavorite = false,
        img = "https://picsum.photos/500/300",
        createdAt = "2025-01-01"
    )
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }


    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("تأكيد الحذف") },
            text = { Text("هل أنت متأكد أنك تريد حذف هذا المنشور؟") },
            confirmButton = {
                TextButton(onClick = {
                    viewmodel.deletePost(displayPost.postId)
                    showDeleteConfirm = false
                    expanded = false
                }) {
                    Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                MaterialTheme.colorScheme.cardColorBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(10.dp)
            .graphicsLayer { clip = false }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(displayPost.img.ifEmpty { "https://picsum.photos/500/300" })
                .crossfade(true)
                .build(),
            contentDescription = displayPost.title,
            placeholder = painterResource(R.drawable.beach_svgrepo_com),
            error = painterResource(R.drawable.beach_svgrepo_com),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(65.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color.White.copy(alpha = 0.15f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (displayPost.user.username == Utils.username)
                    IconButton(
                        onClick = {
                            expanded = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreHoriz,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(180.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.edit)) },
                        onClick = {
                            NavigationHelper.selectedPostForEdit = displayPost
                            navController.navigate(
                                EditPostRoute(postId = displayPost.postId)
                            )
                            expanded = false
                        },
                        leadingIcon = { Icon(Icons.Default.Edit, null) }
                    )
                    Divider()
                    DropdownMenuItem(
                        text = { Text("حذف", color = MaterialTheme.colorScheme.error) },
                        onClick = {
                            showDeleteConfirm = true
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error)
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    MyText(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        title = displayPost.user.fullName,
                        color = Color.White,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        size = 11.sp
                    )
                    MyText(
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                        title = "@${displayPost.user.username}",
                        color = Color.Black,
                        size = 11.sp
                    )
                }
                10.horizontalSpace()
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(displayPost.user.avatar.ifEmpty { "https://picsum.photos/500/300" })
                        .crossfade(true)
                        .build(),
                    contentDescription = displayPost.user.fullName,
                    placeholder = painterResource(R.drawable.beach_svgrepo_com),
                    error = painterResource(R.drawable.beach_svgrepo_com),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                )
            }
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.dp)
                .offset(y = 30.dp)
                .shadow(5.dp, RoundedCornerShape(25.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(25.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .background(MaterialTheme.colorScheme.cardColorBackground),
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
//                            onComment()
                            isSheetOpen.value = true
                        }
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 10.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = null,
                    )
                    5.horizontalSpace()
                    MyText(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        title = stringResource(R.string.comments),
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                )
                TimeAgoText(
                    iso8601String = displayPost.createdAt,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                )
            }
        }


    }

    if (isSheetOpen.value) {
        CommentsBottomSheet(
            postId = displayPost.postId,
            onDismiss = { isSheetOpen.value = false },
            viewmodel = viewmodel
        )
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeAgoText(iso8601String: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val timeAgo = remember(iso8601String) {
        formatTimeAgo(iso8601String, context)
    }

    Text(
        text = timeAgo,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium
    )
}

