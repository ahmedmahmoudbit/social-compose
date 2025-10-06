package com.example.myapplication.ui.home.presentation.widgets.comment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.myapplication.ui.auth.data.models.AddCommentRequest
import com.example.myapplication.ui.home.presentation.manager.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    postId: String,
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    viewmodel: HomeViewModel,
) {

    LaunchedEffect(postId) {
        viewmodel.clearCommentsState()
        viewmodel.fetchComments(postId)
    }

    ModalBottomSheet(
        onDismissRequest = {
            viewmodel.clearCommentsState()
            onDismiss()
        },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        CommentsContent(
            viewModel = viewmodel,
            onSendComment = { text ->
                viewmodel.addComment(AddCommentRequest(postId = postId, text = text))
            },
            onDismiss = {
                viewmodel.clearCommentsState()
                onDismiss()
            }
        )
    }
}