package com.example.myapplication.simple_api.ui

import android.graphics.Color.green
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.simple_api.model.DataModel
import com.example.myapplication.simple_api.viewModel.PostViewModel
import java.nio.file.WatchEvent

@Composable
fun PostsScreen (viewModel: PostViewModel = PostViewModel() , paddingList: PaddingValues) {
    val posts by viewModel.posts

    if (posts.isEmpty()) {
//        CircularProgressIndicator()

//        CircularProgressIndicator(
//            modifier = Modifier.fillMaxSize()
//        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingList),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn (
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(paddingList)

        ){
            items(posts) { post ->
                PostItem(post = post)
            }

        }
    }
}

@Composable
fun PostItem(post: DataModel) {
    Card (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(Color(0xFFFEDCBA))
    ) {
        Column (
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(10.dp)
        ){
            Text(text = post.postId.toString(), style = TextStyle(
                color = Red,
                textAlign = TextAlign.End,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ))
            Text(text = post.title,style = TextStyle(
                color = Gray,
                textAlign = TextAlign.End,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ))
            Text(text = post.body,style = TextStyle(
                color = Black,
                textAlign = TextAlign.End,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ))
        }
    }
}