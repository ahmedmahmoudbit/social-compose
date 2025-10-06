package com.example.myapplication.ui.home.presentation.widgets.comment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.home.data.model.CommentsResponse
import com.example.myapplication.ui.home.presentation.manager.HomeViewModel
import com.example.myapplication.utils.components.page.LoadingPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentsContent(
    viewModel: HomeViewModel,
    onSendComment: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val response = viewModel.comments.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding()
    ) {

        Text(
            text = stringResource(R.string.comments),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (response.value) {
            is DataState.Init -> {}
            is DataState.Loading -> {
                LoadingPage()
            }
            is DataState.Success -> {
                val comments = (response.value as DataState.Success<CommentsResponse>).data.comments
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(comments) { comment ->
                        CommentItem(comment = comment)
                    }
                }
            }
            is DataState.Error -> {
                Text(text =  (response.value as DataState.Error).error, color = MaterialTheme.colorScheme.error)
            }
        }

        var commentText by remember { mutableStateOf("") }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            OutlinedTextField(
                value = commentText,
                onValueChange = { commentText = it },
                placeholder = { Text(stringResource(R.string.add_new_comment)) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp)
            )
            IconButton(
                onClick = {
                    if (commentText.isNotBlank()) {
                        onSendComment(commentText)
                        commentText = ""
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = stringResource(R.string.send_comment)
                )
            }
        }
    }
}