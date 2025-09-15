package com.example.myapplication.utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.mainColor

@Preview(showBackground = true)
@Composable
fun OTPInputPreview() {
    var otpText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MyText(
                title = "Enter OTP",
                color = Color.Black,
                size = 24.sp
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OTPInput(
                otpText = otpText,
                otpCount = 6,
                onOtpTextChange = { value, isComplete ->
                    otpText = value
                    errorMessage = if (isComplete && value != "123456") {
                        "Invalid OTP"
                    } else null
                },
                errorMessage = errorMessage
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (errorMessage != null) {
                MyText(
                    title = errorMessage ?: "",
                    color = Color.Red,
                    size = 14.sp
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            MyButton(
                text = "Verify OTP",
                onClick = {
                    if (otpText.length == 6) {
                        // Simulate verification
                        errorMessage = if (otpText == "123456") {
                            null
                        } else {
                            "Invalid OTP"
                        }
                    }
                },
                buttonColor = mainColor,
                textColor = Color.White,
                isDisabled = otpText.length != 6,
                borderRadius = 8.dp.value
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OTPBottomSheetPreview() {
    var showBottomSheet by remember { mutableStateOf(true) }
    
    if (showBottomSheet) {
        OTPBottomSheet(
            isVisible = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            onOTPVerified = { otp ->
                // Handle verification
                showBottomSheet = false
            },
            email = "user@example.com",
            isLoading = false,
            errorMessage = null
        )
    }
}
