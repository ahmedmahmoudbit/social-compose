package com.example.myapplication.ui.home.presentation.pages

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.PostRequest
import com.example.myapplication.ui.auth.data.models.UpdateRequest
import com.example.myapplication.ui.home.presentation.manager.HomeViewModel
import com.example.myapplication.utils.components.AppForm
import com.example.myapplication.utils.components.MyButton
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import verticalSpace
import java.io.File

/**
 * AddPostScreen - Screen for adding/editing posts
 * @param showBackButton Controls visibility of back button in TopAppBar
 * - true: Show back button (when navigated from other screens or editing)
 * - false: Hide back button (when accessed from navbar tabs)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    isEdit: Boolean = false,
    postId: String = "",
    initialTitle: String = "",
    initialDesc: String = "",
    initialImageUrl: String = "",
    showBackButton: Boolean = true
) {

    val response = viewModel.message.collectAsState()
    val stateResponse = response.value

    val titleController = remember { mutableStateOf(initialTitle) }
    val descriptionController = remember { mutableStateOf(initialDesc) }
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedImageUri = uri }
    )

    LaunchedEffect(Unit) {
        viewModel.clearMessageState()
    }

    LaunchedEffect(stateResponse) {
        when (val data = stateResponse) {
            is DataState.Success -> {
                Toast.makeText(
                    context,
                    data.data.message,
                    Toast.LENGTH_SHORT
                ).show()
                delay(500)
                viewModel.forceRefreshFromServer()
                
                if (!isEdit) {
                    titleController.value = ""
                    descriptionController.value = ""
                    selectedImageUri = null
                } else {
                    // Navigate back for edit posts
                    navController.popBackStack()
                }
                viewModel.clearMessageState()
            }
            is DataState.Error -> {
                Toast.makeText(
                    context,
                    data.error,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearMessageState()
            }
            else -> {
            }
        }
    }

    Scaffold(
        topBar = {
            if (isEdit) {
                TopAppBar(
                    title = {
                        if (isEdit)
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "الرجوع للخلف",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                    },
                    navigationIcon = {
                        if (showBackButton) {
                            @androidx.compose.runtime.Composable {
                                IconButton(
                                    onClick = {
                                        // Clear any pending states and go back
                                        viewModel.clearMessageState()
                                        navController.popBackStack()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "الرجوع للخلف",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        } else {
                            null
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            } else null
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                .clickable { imagePickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else if (isEdit && initialImageUrl.isNotEmpty()) {
                AsyncImage(
                    model = initialImageUrl,
                    contentDescription = "Current Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Add Image",
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        10.verticalSpace()

        AppForm(
            controller = titleController,
            onChanged = { titleController.value = it },
            hintText = stringResource(R.string.enter_your_title),
            type = KeyboardType.Text,
            textInputAction = ImeAction.Next,
            validator = {
                if (it.isEmpty()) context.getString(R.string.please_fill_all_fields) else null
            }
        )
        10.verticalSpace()

        AppForm(
            controller = descriptionController,
            maxLines = 5,
            onChanged = { descriptionController.value = it },
            hintText = stringResource(R.string.enter_your_description),
            type = KeyboardType.Text,
            textInputAction = ImeAction.Next,
            validator = {
                if (it.isEmpty()) context.getString(R.string.please_fill_all_fields) else null
            }
        )
        10.verticalSpace()

        MyButton(
            text = if (stateResponse is DataState.Loading) {
                if (isEdit) "جاري التحديث..." else "جاري النشر..."
            } else {
                if (isEdit) "تحديث المنشور" else "نشر"
            },
            isLoading = stateResponse is DataState.Loading,
            onClick = {
                val avatarPart = selectedImageUri?.let { uri ->
                    try {
                        val contentResolver = context.contentResolver
                        val inputStream = contentResolver.openInputStream(uri)
                        val file =
                            File(context.cacheDir, "avatar_${System.currentTimeMillis()}.jpg")
                        inputStream?.copyTo(file.outputStream())
                        inputStream?.close()

                        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData("img", file.name, requestBody)
                    } catch (e: Exception) {
                        null
                    }
                }
                val updateRequest = PostRequest(
                    title = titleController.value,
                    desc = descriptionController.value,
                    img = avatarPart,
                )
                
                if (isEdit) {
                    viewModel.updatePost(postId, updateRequest)
                } else {
                    viewModel.addPost(updateRequest)
                }
            },
        )

        }
    }
}