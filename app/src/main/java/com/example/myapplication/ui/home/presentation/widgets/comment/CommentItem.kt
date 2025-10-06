package com.example.myapplication.ui.home.presentation.widgets.comment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.home.data.model.Comment
import com.example.myapplication.ui.home.presentation.widgets.TimeAgoText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = comment.avatar,
            contentDescription = null,
            placeholder = painterResource(R.drawable.beach_svgrepo_com),
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = comment.fullName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = comment.text,
                style = MaterialTheme.typography.bodySmall
            )
            TimeAgoText(
                iso8601String = comment.createdAt,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}