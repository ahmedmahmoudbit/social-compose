package com.example.myapplication.utils.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.ui.theme.mainColor

    @Composable
    fun SelectImageCircle(
        imageUri: Uri?,
        onImagePicked: (Uri) -> Unit,
        pickImageLauncher: ManagedActivityResultLauncher<String, Uri?>
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, mainColor.copy(.8f), CircleShape)
                .clickable {
                    pickImageLauncher.launch("image/*")
                }
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberImagePainter(imageUri),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "Pick Image",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp),
                    tint = mainColor
                )
            }
        }
    }