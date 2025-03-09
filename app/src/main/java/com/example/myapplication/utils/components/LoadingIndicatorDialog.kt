package com.example.myapplication.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingIndicatorDialog(
    isVisible: Boolean,
    onDismissRequest: () -> Unit = {}
) {
    if (isVisible) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(80.dp)
                        .height(100.dp)
                        .padding(8.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "جارٍ التحميل...",  // استخدم Localization عند الحاجة
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}


// How to use :
//var showDialog by remember { mutableStateOf(false) }
//
//Button(onClick = { showDialog = true }) {
//    Text("عرض التحميل")
//}
//
//LoadingIndicatorDialog(isVisible = showDialog) {
//    showDialog = false  // لإغلاق الـ Dialog
//}
