package com.example.myapplication.utils.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myapplication.R
import com.example.myapplication.ui.theme.mainColor

@Composable
fun MyButton(
    text: String,
    onClick: (() -> Unit)? = null,
    textColor: Color = Color.White,
    width: Int? = null,
    height: Int = 44,
    borderRadius: Float = 4f,
    isLoading: Boolean = false,
    isDisabled: Boolean = false,
    buttonColor: Color = mainColor,
    colorIndicator: Color = Color.White,
    widgetRight: (@Composable () -> Unit)? = null
) {
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading)
    )
    Button(
        onClick = { if (!isLoading && !isDisabled) onClick?.invoke() },
        shape = RoundedCornerShape(borderRadius.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = buttonColor.copy(alpha = 0.6f),
            disabledContentColor = textColor.copy(alpha = 0.6f)
        ),
        modifier = Modifier.padding(8.dp)

            .height(height.dp)
            .then(if (width != null) Modifier.width(width.dp) else Modifier.fillMaxWidth()),
        enabled = !isDisabled && !isLoading,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center
            )
            widgetRight?.invoke()
            if (isLoading) {
                Spacer(modifier = Modifier.width(10.dp))
                LottieAnimation(
                    composition = lottieComposition,
                    modifier = Modifier.size(50.dp),
                    iterations = LottieConstants.IterateForever,
                    isPlaying = true
                )
//                CircularProgressIndicator(
//                    color = colorIndicator,
//                    strokeWidth = 2.dp,
//                    modifier = Modifier.size(20.dp)
//                )
            }
        }
    }
}
