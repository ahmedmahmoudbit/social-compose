package com.example.myapplication.utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.mainColor
import com.example.myapplication.utils.validators.OTPValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onOTPVerified: (String) -> Unit,
    email: String = "",
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onResendCode: (() -> Unit)? = null,
    isResendLoading: Boolean = false
) {
    var otpValue by remember { mutableStateOf("") }
    var isOTPComplete by remember { mutableStateOf(false) }
    var resendTimer by remember { mutableStateOf(60) }
    var canResend by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    // Timer for resend functionality
    LaunchedEffect(isVisible) {
        if (isVisible) {
            resendTimer = 60
            canResend = false
            while (resendTimer > 0) {
                delay(1000)
                resendTimer--
            }
            canResend = true
        }
    }

    // Auto-verify when OTP is complete
    LaunchedEffect(isOTPComplete, otpValue) {
        if (isOTPComplete && otpValue.length == 6) {
            delay(500)
            onOTPVerified(otpValue)
        }
    }

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                keyboardController?.hide()
                onDismiss()
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = null,
//            windowInsets = WindowInsets.ime
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                MyText(
                    title = stringResource(R.string.verify_email),
                    color = Color.Black,
                    size = 24.sp,
                    fontWeight = FontWeight.Bold,
                    align = TextAlign.Center
                )
                MyText(
                    title = stringResource(R.string.enter_verification_code_sent_to),
                    color = Color.Gray,
                    size = 16.sp,
                    fontWeight = FontWeight.Normal,
                    align = TextAlign.Center,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(5.dp))
                MyText(
                    title = email,
                    color = Color.Gray,
                    size = 16.sp,
                    fontWeight = FontWeight.Normal,
                    align = TextAlign.Center,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(8.dp))
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    OTPInput(
                        otpText = otpValue,
                        otpCount = 6,
                        onOtpTextChange = { value, isComplete ->
                            otpValue = value
                            isOTPComplete = isComplete
                        },
                        errorMessage = errorMessage,
                    )
                }


                // Error message
                if (errorMessage != null) {
                    MyText(
                        title = errorMessage,
                        color = Color.Red,
                        size = 14.sp,
                        fontWeight = FontWeight.Normal,
                        align = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                MyButton(
                    text = stringResource(R.string.verify_code),
                    isLoading = isLoading,
                    onClick = {
                        val validationError = OTPValidator.validateOTP(otpValue, context)
                        if (validationError == null) {
                            onOTPVerified(otpValue)
                        }
                    },
                    buttonColor = mainColor,
                    textColor = Color.White,
                    isDisabled = !OTPValidator.isValidOTP(otpValue),
                    borderRadius = 8.dp.value
                )

                // Resend Code
                if (canResend) {
                    MyButton(
                        text = stringResource(R.string.resend_code),
                        isLoading = isResendLoading,
                        onClick = {
                            otpValue = ""
                            isOTPComplete = false
                            resendTimer = 60
                            canResend = false
                            // Call the provided callback or use default behavior
                            onResendCode?.invoke()
                        },
                        buttonColor = Color.Transparent,
                        textColor = mainColor,
                        borderRadius = 8.dp.value
                    )
                } else {
                    MyText(
                        title = stringResource(R.string.resend_code_in) + " ${resendTimer}s",
                        color = Color.Gray,
                        size = 14.sp,
                        fontWeight = FontWeight.Normal,
                        align = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
