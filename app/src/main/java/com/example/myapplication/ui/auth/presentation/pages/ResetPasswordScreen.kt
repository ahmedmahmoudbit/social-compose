package com.example.myapplication.ui.auth.presentation.pages

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
import com.example.myapplication.ui.theme.mainColor
import com.example.myapplication.utils.components.AppForm
import com.example.myapplication.utils.components.MyButton
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.components.OTPBottomSheet

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val forgetStates = viewModel.forgetStates.collectAsState()
    val verifyStates = viewModel.verifyStates.collectAsState()

    val context = LocalContext.current

    val emailController = remember { mutableStateOf("") }
    val passwordCodeController = remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
    var showOTPBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(forgetStates.value) {
        when (val data = forgetStates.value) {
            DataState.Init -> {
                Log.i(TAG, "ResetPasswordScreen: Init ")
            }

            DataState.Loading -> {
                Log.i(TAG, "ResetPasswordScreen: Loading ")
            }

            is DataState.Error -> {
                Toast.makeText(
                    context,
                    data.error,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is DataState.Success -> {
                showOTPBottomSheet = true
                Toast.makeText(
                    context,
                    data.data,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    LaunchedEffect(verifyStates.value) {
        when (val data = verifyStates.value) {
            DataState.Init -> {
                Log.i(TAG, "ResetPasswordScreen: Init ")
            }

            DataState.Loading -> {
                Log.i(TAG, "ResetPasswordScreen: Loading ")
            }

            is DataState.Error -> {
                Toast.makeText(
                    context,
                    data.error,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is DataState.Success -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.changed_password_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        MyText(
            title = "استعادة كلمة المرور",
            size = 28.sp,
            fontWeight = FontWeight.Bold,
            color = mainColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        MyText(
            title = "أدخل بريدك الإلكتروني وسنرسل لك رمز التحقق",
            size = 16.sp,
            color = Color.Gray,
            align = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        AppForm(
            controller = emailController,
            onChanged = {
                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            hintText = stringResource(R.string.enter_email),
            type = KeyboardType.Email,
            textInputAction = ImeAction.Done,

            onSubmit = { focusManager.clearFocus() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        AppForm(
            controller = passwordCodeController,
            onChanged = {
                passwordCodeController.value = it
            },
            hintText = stringResource(R.string.enter_new_password),
            type = KeyboardType.Email,
            textInputAction = ImeAction.Done,

            onSubmit = { focusManager.clearFocus() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        MyButton(
            text = when (forgetStates.value) {
                is DataState.Loading -> "جاري الإرسال..."
                else -> "إرسال رمز التحقق"
            },
            onClick = {
                if (emailController.value.isNotEmpty() && isEmailValid) {
                    viewModel.forgetPassword(emailController.value)
                }
            },
            isLoading = forgetStates.value is DataState.Loading,
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = { navController.popBackStack() }
        ) {
            MyText(
                title = "العودة لتسجيل الدخول",
                color = mainColor,
                size = 16.sp
            )
        }
    }


    if (showOTPBottomSheet) {
        OTPBottomSheet(
            isVisible = showOTPBottomSheet,
            onDismiss = {
                showOTPBottomSheet = false
            },
            onOTPVerified = { otpCode ->
                viewModel.verifyPassword(
                    email = emailController.value,
                    code = otpCode,
                    newPassword = passwordCodeController.value,
                )
            },
            email = emailController.value,
            isLoading = verifyStates.value is DataState.Loading,
            onResendCode = {
                viewModel.forgetPassword(emailController.value)
            },
            isResendLoading = verifyStates.value is DataState.Loading
        )
    }




}