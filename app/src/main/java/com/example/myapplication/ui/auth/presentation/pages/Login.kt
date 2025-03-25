package com.example.myapplication.ui.auth.presentation.pages

import CacheHelper
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.myapplication.utils.CacheString
import com.example.myapplication.utils.components.AppForm
import com.example.myapplication.utils.components.MyButton
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.navigation.RouteRegister


@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val loginResponse = viewModel.login.collectAsState()
    val context = LocalContext.current
    val response = loginResponse.value
    val lottieLogin by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.login))
    val emailController = remember { mutableStateOf("") }
    val passwordController = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(loginResponse.value) {
        when (val data = loginResponse.value) {
            is LoginState.Error -> {
                Toast.makeText(
                    context,
                    data.error,
                    Toast.LENGTH_SHORT
                ).show()
            }

            LoginState.Init -> {
                Log.i(TAG, "LoginScreen: Init ");
            }

            LoginState.Loading -> {
                Log.i(TAG, "LoginScreen: Loading ");
            }

            is LoginState.Success -> {
                CacheHelper(context).setData(CacheString.token,data.data.token)
                navController.navigate(RouteRegister)
//                Log.i(TAG, "LoginScreen: ${data.data.l} ");
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {}
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = lottieLogin,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .height(350.dp)
                )

                MyText(
                    title = stringResource(R.string.login_account),
                    color = Color(0xFFf5511e),
                    size = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                MyText(
                    title = stringResource(R.string.welcome_to_our_team_in_geecoders),
                    color = Color.DarkGray,
                    size = 12.sp,
                )

                Spacer(modifier = Modifier.height(15.dp))

                AppForm(
                    controller = emailController,
                    onChanged = { emailController.value = it },
                    hintText = stringResource(R.string.enter_email),
                    type = KeyboardType.Text,
                    textInputAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(5.dp))

                AppForm(
                    controller = passwordController,
                    onChanged = { passwordController.value = it },
                    hintText = stringResource(R.string.enter_password),
                    isPassword = true,
                    type = KeyboardType.Password,
                    textInputAction = ImeAction.Done,
                    onSubmit = { focusManager.clearFocus() }
                )

                Spacer(modifier = Modifier.height(10.dp))

                MyButton(
                    text = stringResource(R.string.login),
                    isLoading = response is LoginState.Loading,
                    onClick = {
                        if (emailController.value.trim()
                                .isEmpty() || passwordController.value.trim().isEmpty()
                        ) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_fill_all_fields),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@MyButton
                        } else {
                            val data = LoginRequest(
                                email = emailController.value,
                                password = passwordController.value
                            );
                            viewModel.login(data); }
                       

//                        when (val response = loginResponse.value) {
//                            is ResourceState.Success -> {
//                                val data = response.data
//
//                                Toast.makeText(
//                                    context,
//                                    "Welcome ${data.data.user.name}",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                            is ResourceState.Error -> {
//                                // معالجة الأخطاء في حالة الفشل
//                                Toast.makeText(
//                                    context,
//                                    "Error: ${response.error}",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                            is ResourceState.Loading -> {
//                                Toast.makeText(
//                                    context,
//                                    "Loading...",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                            is ResourceState.Init -> {}
//                        }
                    },
                    buttonColor = Color(0xFFf5511e),
                    textColor = Color.White,
                    borderRadius = 8.dp.value
                )
                Spacer(modifier = Modifier.height(5.dp))
                TextButton(
                    onClick = {
                        navController.navigate(RouteRegister)


//                        navController.navigate(RouteRegister())


//                        if (loginResponse.value is LoginState.Success) {
//                            val response = loginResponse.value as ResourceState.Success<LoginResponse>
//                            val data = response.data
//                            Toast.makeText(
//                                context,
//                                "Welcome ${data.data.user.name}",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }

//                        when (val response = loginResponse.value) {
//                            is ResourceState.Success -> {
//                                val data = response.data
//
//                                Toast.makeText(
//                                    context,
//                                    "Welcome ${data.data.user.name}",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                            is ResourceState.Error -> {
//                                // معالجة الأخطاء في حالة الفشل
//                                Toast.makeText(
//                                    context,
//                                    "Error: ${response.error}",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                            is ResourceState.Loading -> {
//                                // يمكنك إظهار رسالة تحميل أو شيء مشابه هنا
//                                Toast.makeText(
//                                    context,
//                                    "Loading...",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                            is ResourceState.Init -> {}
//                        }

//                        navController.navigate("register")
                    }
                ) {
                    MyText(
                        title = stringResource(R.string.create_account),
                        color = Color(0xFFf5511e),
                        size = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}
