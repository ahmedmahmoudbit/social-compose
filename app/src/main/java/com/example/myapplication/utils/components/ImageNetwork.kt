package com.example.myapplication.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlin.text.ifEmpty

@Composable
fun ImageNetwork(
    image: String = "",
    size: Dp = 50.dp,
    rounded: Dp? = null,
    onClick: (() -> Unit)? = null
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.ifEmpty { "https://picsum.photos/500/300" })
            .crossfade(true)
            .build(),
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(size)
            .clip(if (rounded!=null) RoundedCornerShape(rounded) else CircleShape)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        contentScale = ContentScale.Crop
    )
}