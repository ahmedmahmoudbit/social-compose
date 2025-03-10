package com.example.myapplication.ui.auth.presentation.pages

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.data.models.RegisterRequest
import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
import com.example.myapplication.ui.theme.mainColor
import com.example.myapplication.utils.components.AppForm
import com.example.myapplication.utils.components.MyButton
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.components.MyTopAppBar
import com.example.myapplication.utils.components.SelectImageCircle
import com.example.myapplication.utils.navigation.RouteRegister
import com.example.myapplication.utils.navigation.RouteSuccessScreen
import com.example.myapplication.utils.resource.Utils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val registerResponse = viewModel.register.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val response = registerResponse.value
    val lottieLogin by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.register)
    )
    val emailController = remember { mutableStateOf("") }
    val usernameController = remember { mutableStateOf("") }
    val nameController = remember { mutableStateOf("") }
    val phoneController = remember { mutableStateOf("") }
    val passwordController = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    var isFormValid by remember { mutableStateOf(false) }

    fun validateForm(): Boolean {
        return listOf(
            nameController.value.isNotBlank(),
            emailController.value.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(
                emailController.value
            ).matches(),
            phoneController.value.isNotBlank() && phoneController.value.length >= 10,
            passwordController.value.length >= 6,
            confirmPassword.value == passwordController.value
        ).all { it }
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
        }
    }
    LaunchedEffect(
        nameController.value,
        emailController.value,
        phoneController.value,
        passwordController.value,
        confirmPassword.value
    ) {
        isFormValid = validateForm()
    }

    LaunchedEffect(registerResponse.value) {
        when (val res = registerResponse.value) {
            is LoginState.Error -> {
            }
            LoginState.Init -> {
            }
            LoginState.Loading -> {
            }
            is LoginState.Success -> {
                navController.navigate(RouteSuccessScreen(title = res.data.message)) {
                    popUpTo(RouteRegister::class) {
                        inclusive = true
                    }
                }

            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.register),
                navController,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                LottieAnimation(
//                    composition = lottieLogin,
//                    iterations = LottieConstants.IterateForever,
//                    modifier = Modifier.height(300.dp)
//                )

                SelectImageCircle(
                    imageUri = imageUri,
                    onImagePicked = {
                        imageUri = it
                    },
                    pickImageLauncher = pickImageLauncher
                )

                Spacer(modifier = Modifier.height(15.dp))

                MyText(
                    title = stringResource(R.string.create_account_and_start_new_journey),
                    color = mainColor,
                    size = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(15.dp))

                AppForm(
                    controller = nameController,
                    onChanged = { nameController.value = it },
                    hintText = stringResource(R.string.enter_name),
                    type = KeyboardType.Text,
                    textInputAction = ImeAction.Next,
                    validator = { if (it.isEmpty()) context.getString(R.string.name_is_required) else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

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

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = usernameController,
                    onChanged = { usernameController.value = it },
                    hintText = stringResource(R.string.enter_username),
                    type = KeyboardType.Number,
                    textInputAction = ImeAction.Next,
                    validator = { input ->
                        when {
                            input.isEmpty() -> context.getString(R.string.username_cannot_be_empty)
                            !input.matches("^[a-zA-Z0-9_]{5,20}$".toRegex()) ->
                                context.getString(R.string.username_must_be_5_20_characters_long_and_contain_only_letters_numbers_and_underscores)

                            else -> null
                        }
                    })


                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = phoneController,
                    onChanged = { phoneController.value = it },
                    hintText = "Enter Phone",
                    type = KeyboardType.Number,
                    textInputAction = ImeAction.Next,
                    validator = { if (it.isEmpty() || it.length < 10) context.getString(R.string.phone_number_must_be_at_least_10_digits) else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = passwordController,
                    onChanged = { passwordController.value = it },
                    hintText = "Enter Password",
                    type = KeyboardType.Password,
                    textInputAction = ImeAction.Next,
                    validator = { if (it.length < 6) context.getString(R.string.password_must_be_at_least_6_characters) else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = confirmPassword,
                    onChanged = { confirmPassword.value = it },
                    hintText = stringResource(R.string.confirm_password),
                    type = KeyboardType.Password,
                    textInputAction = ImeAction.Done,
                    validator = { if (it != passwordController.value) context.getString(R.string.passwords_do_not_match) else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

                MyButton(
                    text = stringResource(R.string.register),
                    isLoading = response is LoginState.Loading,
                    onClick = {
                        if (isFormValid) {
                            val avatar = imageUri?.let { Utils.imageToMultipart(it, context) }
                            val data = RegisterRequest(
                                email = emailController.value,
                                password = passwordController.value,
                                fullName = nameController.value,
                                username = usernameController.value,
                                phoneNumber = phoneController.value,
                                avatar = avatar
                            )
                            viewModel.register(data)
                        }
                    },
                    buttonColor = Color(0xFFf5511e),
                    textColor = Color.White,
                    isDisabled = !isFormValid,
                    borderRadius = 8.dp.value
                )

                TextButton(
                    onClick = { navController.popBackStack() }
                ) {
                    MyText(
                        title = stringResource(R.string.back),
                        color = Color(0xFFf5511e),
                        size = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}




