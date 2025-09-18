package com.example.myapplication.ui.home.presentation.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.ui.home.data.model.Post
import com.example.myapplication.ui.home.data.model.User
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.resource.Utils

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CardPost(
    modifier: Modifier = Modifier,
    post: Post? = null,
    onComment: () -> Unit = { }
) {
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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
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
                .height(250.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.dp)
                .offset(y = 30.dp)
                .shadow(5.dp, RoundedCornerShape(12.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White),
            ) {
                IconButton(
                    onClick = onComment,
                    modifier = Modifier.width(130.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = null,
                            tint = Color(0xFF000000)
                        )
                        MyText(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically),
                            title = stringResource(R.string.comments),
                            color = Color(0xFF000000)
                        )
                    }


                }
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color(0xFF000000)
                )
                MyText(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    title = Utils.formatTimeAgo(displayPost.createdAt,LocalContext.current),
                    color = Color(0xFF000000)
                )
            }
        }

    }
}
