package com.example.myapplication.ui.auth.presentation.pages
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.myapplication.ui.auth.data.models.AuthState
import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
import com.example.myapplication.ui.theme.mainColor
import com.example.myapplication.utils.components.AppForm
import com.example.myapplication.utils.components.MyText

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val emailController = remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    val authStatus by viewModel.authStatus.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        // العنوان الرئيسي
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
        
        // حقل إدخال البريد الإلكتروني
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
        
//        if (!isEmailValid && email.isNotEmpty()) {
//            MyText(
//                title = "يرجى إدخال بريد إلكتروني صالح",
//                color = Color.Red,
//                size = 12.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, top = 4.dp)
//            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // زر إرسال رمز التحقق
//        MyButton(
//            text = when (authStatus) {
//                is AuthState.Loading -> "جاري الإرسال..."
//                else -> "إرسال رمز التحقق"
//            },
//            onClick = {
//                if (email.isNotEmpty() && isEmailValid) {
//                    viewModel.forgetPassword(email)
//                }
//            },
//            enable = email.isNotEmpty() && isEmailValid && authStatus !is AuthState.Loading,
//            modifier = Modifier.fillMaxWidth()
//        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
//        if (authStatus is AuthState.Success) {
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f))
//            ) {
//                MyText(
//                    title = authStatus.data,
//                    color = Color.Green,
//                    size = 14.sp,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//        }
        
        // رسالة الخطأ
        if (authStatus is AuthState.Error) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
            ) {
                MyText(
                    title = "authStatus.error",
                    color = Color.Red,
                    size = 14.sp,
                    align = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // زر العودة لتسجيل الدخول
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