package com.example.myapplication.ui.home.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Article
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.home.presentation.widgets.CardPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header with Add Post Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Article,
                    contentDescription = "Posts",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "المنشورات",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            FloatingActionButton(
                onClick = { /* TODO: Handle add post */ },
                modifier = Modifier.size(48.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Post",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        
        // Posts List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(15) { index ->
                CardPost(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    post = null // استخدام البيانات التجريبية
                )
            }
        }
    }
}