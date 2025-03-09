package com.example.myapplication.ui.auth.presentation.pages

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.LoginRequest
import com.example.myapplication.ui.auth.data.models.LoginState
import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
import com.example.myapplication.utils.components.AppForm
import com.example.myapplication.utils.components.MyButton
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.components.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val loginResponse = viewModel.login.collectAsState()
    val context = LocalContext.current

    val response = loginResponse.value
    val lottieLogin by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.register)
    )
    val emailController = remember { mutableStateOf("") }
    val nameController = remember { mutableStateOf("") }
    val phoneController = remember { mutableStateOf("") }
    val passwordController = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    var isFormValid by remember { mutableStateOf(false) }

    fun validateForm(): Boolean {
        return listOf(
            nameController.value.isNotBlank(),
            emailController.value.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(emailController.value).matches(),
            phoneController.value.isNotBlank() && phoneController.value.length >= 10,
            passwordController.value.length >= 6,
            confirmPassword.value == passwordController.value
        ).all { it }
    }

    LaunchedEffect(nameController.value, emailController.value, phoneController.value, passwordController.value, confirmPassword.value) {
        isFormValid = validateForm()
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
                LottieAnimation(
                    composition = lottieLogin,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.height(300.dp)
                )

                MyText(
                    title = "Create Account Page",
                    color = Color(0xFFf5511e),
                    size = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = nameController,
                    onChanged = { nameController.value = it },
                    hintText = "Enter Name",
                    type = KeyboardType.Text,
                    textInputAction = ImeAction.Next,
                    validator = { if (it.isEmpty()) "Name is required" else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = emailController,
                    onChanged = { emailController.value = it },
                    hintText = "Enter Email",
                    type = KeyboardType.Email,
                    textInputAction = ImeAction.Next,
                    validator = { if (it.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Invalid email" else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = phoneController,
                    onChanged = { phoneController.value = it },
                    hintText = "Enter Phone",
                    type = KeyboardType.Number,
                    textInputAction = ImeAction.Next,
                    validator = { if (it.isEmpty() || it.length < 10) "Phone number must be at least 10 digits" else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = passwordController,
                    onChanged = { passwordController.value = it },
                    hintText = "Enter Password",
                    type = KeyboardType.Password,
                    textInputAction = ImeAction.Next,
                    validator = { if (it.length < 6) "Password must be at least 6 characters" else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = confirmPassword,
                    onChanged = { confirmPassword.value = it },
                    hintText = "Confirm Password",
                    type = KeyboardType.Password,
                    textInputAction = ImeAction.Done,
                    validator = { if (it != passwordController.value) "Passwords do not match" else null }
                )

                Spacer(modifier = Modifier.height(5.dp))

                MyButton(
                    text = "Register",
                    isLoading = response is LoginState.Loading,
                    onClick = {
                        if (isFormValid) {
                            Toast.makeText(
                                context,
                                "Welcome ${nameController.value}",
                                Toast.LENGTH_SHORT
                            ).show()
                            val data = LoginRequest(
                                email = emailController.value,
                                password = passwordController.value
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
                        title = "Back",
                        color = Color(0xFFf5511e),
                        size = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
