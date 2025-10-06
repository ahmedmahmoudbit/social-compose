package com.example.myapplication.ui.users.presentation.pages

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.UpdateRequest
import com.example.myapplication.ui.home.presentation.manager.HomeViewModel
import com.example.myapplication.ui.home.presentation.manager.ProfileViewModel
import com.example.myapplication.ui.users.data.model.Users
import com.example.myapplication.utils.components.AppForm
import com.example.myapplication.utils.components.MyButton
import com.example.myapplication.utils.components.MyTopAppBar
import com.example.myapplication.utils.navigation.ChangeProfileRoute
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import verticalSpace
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    navController: NavHostController = rememberNavController(),
    it: NavBackStackEntry,
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(),
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val context = LocalContext.current
    val route = it.toRoute<ChangeProfileRoute>()
    val userData = Json.decodeFromString<Users>(route.data)
    val viewModelData = viewModel.message.collectAsState()
    val response = viewModelData.value

    // Form states
    val fullNameController = remember { mutableStateOf(userData.fullName) }
    val emailController = remember { mutableStateOf(userData.email) }
    val phoneController = remember { mutableStateOf(userData.phone!!) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedImageUri = uri }
    )

    // Clear any previous message state when entering this screen
    LaunchedEffect(Unit) {
        viewModel.clearMessage()
    }

    LaunchedEffect(response) {
        when (val data = response) {
            is DataState.Success -> {
                // Show success message
                Toast.makeText(
                    context,
                    data.data.message,
                    Toast.LENGTH_SHORT
                ).show()
                
                // Clear the message state and refresh data
                viewModel.clearMessage()
                
                // Force refresh profile data from server (clear cache and reload)
                viewModel.refreshProfile()
                
                // Also refresh posts data to update user info in posts
                homeViewModel.refreshData()
                
                // Small delay to ensure state is cleared before navigation
                delay(100)
                navController.popBackStack()
            }

            is DataState.Error -> {
                Toast.makeText(
                    context,
                    data.error,
                    Toast.LENGTH_SHORT
                ).show()
                // Clear error state after showing with delay
                delay(100)
                viewModel.clearMessage()
            }

            else -> {
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = stringResource(R.string.change_profile),
                        style = MaterialTheme.typography.titleLarge
                    ) 
                },
                navigationIcon = {
                    IconButton(
                        onClick = { 
                            // Clear any pending states and go back
                            viewModel.clearMessage()
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image Section
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
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
                } else if (userData.avatar.isNotEmpty()) {
                    AsyncImage(
                        model = userData.avatar,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Default Profile",
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = CircleShape
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "تغيير الصورة",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "اضغط لتغيير الصورة",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            AppForm(
                controller = fullNameController,
                onChanged = { fullNameController.value = it },
                hintText = stringResource(R.string.enter_name),
                type = KeyboardType.Text,
                textInputAction = ImeAction.Next,
                validator = {
                    if (it.isEmpty()) context.getString(R.string.name_is_required) else null
                }
            )
            10.verticalSpace()

            AppForm(
                controller = emailController,
                onChanged = { emailController.value = it },
                hintText = stringResource(R.string.enter_email),
                type = KeyboardType.Email,
                textInputAction = ImeAction.Next,
                validator = {
                    if (it.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(
                            it
                        ).matches()
                    ) context.getString(R.string.invalid_email) else null
                }
            )
            10.verticalSpace()

            AppForm(
                controller = phoneController,
                onChanged = { phoneController.value = it },
                hintText = stringResource(R.string.enter_email),
                type = KeyboardType.Phone,
                textInputAction = ImeAction.Next,
                validator = {
                    if (it.isEmpty()) context.getString(R.string.phone_number_must_be_at_least_10_digits) else null
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            MyButton(
                text = if (response is DataState.Loading) "جاري التحديث..." else "تحديث الملف الشخصي",
                isLoading = response is DataState.Loading,
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
                            MultipartBody.Part.createFormData("avatar", file.name, requestBody)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    val updateRequest = UpdateRequest(
                        fullName = fullNameController.value.trim(),
                        email = emailController.value.trim(),
                        phone = phoneController.value.trim(),
                        avatar = avatarPart
                    )
                    viewModel.updateProfile(updateRequest)
                },
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}