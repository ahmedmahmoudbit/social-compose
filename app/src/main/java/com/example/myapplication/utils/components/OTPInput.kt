package com.example.myapplication.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.mainColor

@Composable
fun OTPInput(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit,
    errorMessage: String? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { 
                isFocused = it.isFocused 
            },
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount && it.text.all { char -> char.isDigit() }) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        textStyle = TextStyle(
            textDirection = TextDirection.Ltr,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText,
                        isFocused = isFocused,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    )
    }
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    isFocused: Boolean,
    errorMessage: String?
) {
    val isFilled = text.getOrNull(index)?.toString() ?: ""
    val isCurrentPosition = text.length == index

    val borderColor = when {
        errorMessage != null -> Color.Red
        isFilled.isNotEmpty() -> Color.Transparent
        isFocused && isCurrentPosition -> mainColor
        else -> Color.Gray.copy(alpha = 0.3f)
    }

    val backgroundColor = when {
        isFilled.isNotEmpty() -> mainColor.copy(alpha = 0.1f)
        isFocused && isCurrentPosition -> mainColor.copy(alpha = 0.1f)
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(
                width = if (borderColor == mainColor) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isFilled.isNotEmpty()) {
            Text(
                text = isFilled,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    textDirection = TextDirection.Ltr
                )
            )
        } else if (isFocused && isCurrentPosition) {
            // Cursor effect
            Text(
                text = "|",
                style = TextStyle(
                    color = mainColor,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    textDirection = TextDirection.Ltr
                )
            )
        } else {
            Text(
                text = "-",
                style = TextStyle(
                    color = Color.Gray.copy(alpha = 0.5f),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    textDirection = TextDirection.Ltr
                )
            )
        }
    }
}
