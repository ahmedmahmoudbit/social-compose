package com.example.myapplication.ui.home.presentation.pages

import android.content.ContentValues.TAG
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.auth.data.models.MessageResponse
import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
import com.example.myapplication.ui.home.presentation.manager.ProfileViewModel
import com.example.myapplication.ui.theme.mainColor
import com.example.myapplication.utils.components.AppForm
import com.example.myapplication.utils.components.MyButton
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.components.MyTopAppBar
import com.example.myapplication.utils.components.OTPBottomSheet
import verticalSpace

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>()
) {
    val forgetStates = viewModel.message.collectAsState()
    val context = LocalContext.current
    val oldPasswordController = remember { mutableStateOf("") }
    val newPasswordCodeController = remember { mutableStateOf("") }

    LaunchedEffect(forgetStates.value) {
        when (val data = forgetStates.value) {
            DataState.Init -> {}
            DataState.Loading -> {}
            is DataState.Error -> {
                Log.i(TAG, "ChangePasswordScreen: ${data.error}")
                Toast.makeText(
                    context,
                    data.error,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is DataState.Success -> {
                navController.popBackStack()
                Toast.makeText(
                    context,
                    data.data.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.change_password),
                navController,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                10.verticalSpace()
                AppForm(
                    controller = oldPasswordController,
                    hintText = stringResource(R.string.enter_your_old_password),
                    type = KeyboardType.Password,
                    isPassword = true,
                    textInputAction = ImeAction.Done,
                )
                10.verticalSpace()
                AppForm(
                    controller = newPasswordCodeController,
                    hintText = stringResource(R.string.enter_your_new_password),
                    type = KeyboardType.Email,
                    isPassword = true,
                    textInputAction = ImeAction.Done,
                )
                10.verticalSpace()
                MyButton(
                    text = when (forgetStates.value) {
                        is DataState.Loading -> "جاري الإرسال..."
                        else -> "تغيير"
                    },
                    onClick = {
                        if (oldPasswordController.value.isNotEmpty() && newPasswordCodeController.value.isNotEmpty()) {

                            viewModel.changePassword(
                                oldPassword = oldPasswordController.value,
                                newPassword = newPasswordCodeController.value
                            )
                        } else {
                            Toast.makeText(context, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show()
                        }
                    },
                    isLoading = forgetStates.value is DataState.Loading,
                )
            }
        }
    }
}