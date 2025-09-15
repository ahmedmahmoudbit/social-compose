//package com.example.myapplication.ui.auth.presentation.pagespackage com.example.myapplication.ui.auth.presentation.pagespackage com.example.myapplication.ui.auth.presentation.pagespackage com.example.myapplication.ui.auth.presentation.pagespackage
//
//
//
//import androidx.compose.foundation.background
//
//import androidx.compose.foundation.layout.*
//
//import androidx.compose.foundation.rememberScrollStateimport androidx.compose.foundation.background
//
//import androidx.compose.foundation.shape.RoundedCornerShape
//
//import androidx.compose.foundation.verticalScrollimport androidx.compose.foundation.layout.Column
//
//import androidx.compose.material3.*
//
//import androidx.compose.runtime.*import androidx.compose.foundation.layout.Spacerimport androidx.compose.foundation.backgroundcom.example.myapplication.ui.auth.presentation.pages
//
//import androidx.compose.ui.Alignment
//
//import androidx.compose.ui.Modifierimport androidx.compose.foundation.layout.fillMaxSize
//
//import androidx.compose.ui.graphics.Color
//
//import androidx.compose.ui.text.font.FontWeightimport androidx.compose.foundation.layout.fillMaxWidthimport androidx.compose.foundation.layout.*
//
//import androidx.compose.ui.text.style.TextAlign
//
//import androidx.compose.ui.unit.dpimport androidx.compose.foundation.layout.height
//
//import androidx.compose.ui.unit.sp
//
//import androidx.hilt.navigation.compose.hiltViewModelimport androidx.compose.foundation.layout.paddingimport androidx.compose.foundation.rememberScrollState
//
//import androidx.navigation.NavController
//
//import com.example.myapplication.ui.auth.data.models.AuthStateimport androidx.compose.foundation.rememberScrollState
//
//import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
//
//import com.example.myapplication.ui.component.AppFormimport androidx.compose.foundation.shape.RoundedCornerShapeimport androidx.compose.foundation.shape.RoundedCornerShape
//
//import com.example.myapplication.ui.component.MyButton
//
//import com.example.myapplication.ui.component.MyTextimport androidx.compose.foundation.verticalScroll
//
//import com.example.myapplication.ui.theme.mainColor
//
//import androidx.compose.material3.Cardimport androidx.compose.foundation.verticalScrollimport androidx.compose.foundation.backgroundimport
//
//@Composable
//
//fun ForgotPasswordScreen(import androidx.compose.material3.CardDefaults
//
//    navController: NavController,
//
//    viewModel: LoginViewModel = hiltViewModel()import androidx.compose.material3.ExperimentalMaterial3Apiimport androidx.compose.material3.*
//
//) {
//
//    var email by remember { mutableStateOf("") }import androidx.compose.material3.TextButton
//
//    var isEmailValid by remember { mutableStateOf(true) }
//
//    import androidx.compose.runtime.Composableimport androidx.compose.runtime.*android.util.Log
//
//    val authStatus by viewModel.authStatus.collectAsState()
//
//    import androidx.compose.runtime.LaunchedEffect
//
//    Column(
//
//        modifier = Modifierimport androidx.compose.runtime.collectAsStateimport androidx.compose.ui.Alignment
//
//            .fillMaxSize()
//
//            .background(Color.White)import androidx.compose.runtime.getValue
//
//            .padding(24.dp)
//
//            .verticalScroll(rememberScrollState()),import androidx.compose.runtime.mutableStateOfimport androidx.compose.ui.Modifierimport androidx.compose.foundation.layout.*
//
//        horizontalAlignment = Alignment.CenterHorizontally
//
//    ) {import androidx.compose.runtime.remember
//
//        Spacer(modifier = Modifier.height(60.dp))
//
//        import androidx.compose.runtime.setValueimport androidx.compose.ui.graphics.Colorimport androidx.compose.foundation.background
//
//        MyText(
//
//            title = "استعادة كلمة المرور",import androidx.compose.ui.Alignment
//
//            size = 28.sp,
//
//            fontWeight = FontWeight.Bold,import androidx.compose.ui.Modifierimport androidx.compose.ui.platform.LocalSoftwareKeyboardController
//
//            color = mainColor
//
//        )import androidx.compose.ui.graphics.Color
//
//
//
//        Spacer(modifier = Modifier.height(16.dp))import androidx.compose.ui.platform.LocalSoftwareKeyboardControllerimport androidx.compose.ui.text.font.FontWeightimport androidx.compose.foundation.rememberScrollStateimport
//
//
//
//        MyText(import androidx.compose.ui.text.font.FontWeight
//
//            title = "أدخل بريدك الإلكتروني وسنرسل لك رمز التحقق",
//
//            size = 16.sp,import androidx.compose.ui.text.style.TextAlignimport androidx.compose.ui.text.style.TextAlign
//
//            color = Color.Gray,
//
//            textAlign = TextAlign.Center,import androidx.compose.ui.unit.dp
//
//            modifier = Modifier.padding(horizontal = 16.dp)
//
//        )import androidx.compose.ui.unit.spimport androidx.compose.ui.unit.dpandroidx.compose.foundation.gestures.detectTapGestures
//
//
//
//        Spacer(modifier = Modifier.height(40.dp))import androidx.hilt.navigation.compose.hiltViewModel
//
//
//
//        AppForm(import androidx.navigation.NavControllerimport androidx.compose.ui.unit.sp
//
//            value = email,
//
//            onValueChange = { import com.example.myapplication.ui.auth.data.models.AuthState
//
//                email = it
//
//                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()import com.example.myapplication.ui.auth.presentation.manager.LoginViewModelimport androidx.hilt.navigation.compose.hiltViewModelimport androidx.compose.foundation.shape.RoundedCornerShapeimport
//
//            },
//
//            label = "البريد الإلكتروني",import com.example.myapplication.ui.component.AppForm
//
//            isError = !isEmailValid && email.isNotEmpty(),
//
//            modifier = Modifier.fillMaxWidth()import com.example.myapplication.ui.component.MyButtonimport androidx.navigation.NavController
//
//        )
//
//        import com.example.myapplication.ui.component.MyText
//
//        if (!isEmailValid && email.isNotEmpty()) {
//
//            MyText(import com.example.myapplication.ui.theme.mainColorimport com.example.myapplication.ui.auth.data.models.AuthStateandroidx.compose.foundation.layout.*
//
//                title = "يرجى إدخال بريد إلكتروني صالح",
//
//                color = Color.Red,
//
//                size = 12.sp,
//
//                modifier = Modifier@OptIn(ExperimentalMaterial3Api::class)import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
//
//                    .fillMaxWidth()
//
//                    .padding(start = 16.dp, top = 4.dp)@Composable
//
//            )
//
//        }fun ForgotPasswordScreen(import com.example.myapplication.ui.component.AppFormimport androidx.compose.foundation.verticalScrollimport
//
//
//
//        Spacer(modifier = Modifier.height(32.dp))    navController: NavController,
//
//
//
//        MyButton(    viewModel: LoginViewModel = hiltViewModel()import com.example.myapplication.ui.component.MyButton
//
//            title = when (authStatus) {
//
//                is AuthState.Loading -> "جاري الإرسال...") {
//
//                else -> "إرسال رمز التحقق"
//
//            },    var email by remember { mutableStateOf("") }import com.example.myapplication.ui.component.MyTextandroidx.compose.foundation.layout.Arrangement
//
//            onClick = {
//
//                if (email.isNotEmpty() && isEmailValid) {    var isEmailValid by remember { mutableStateOf(true) }
//
//                    viewModel.forgetPassword(email)
//
//                }    import com.example.myapplication.utils.components.OTPBottomSheet
//
//            },
//
//            enable = email.isNotEmpty() && isEmailValid && authStatus !is AuthState.Loading,    val authStatus by viewModel.authStatus.collectAsState()
//
//            modifier = Modifier.fillMaxWidth()
//
//        )    val keyboardController = LocalSoftwareKeyboardController.currentimport com.example.myapplication.ui.theme.mainColorimport androidx.compose.material3.*
//
//
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//
//
//        if (authStatus is AuthState.Success) {    Column(import androidx.compose.foundation.rememberScrollState
//
//            Card(
//
//                modifier = Modifier.fillMaxWidth(),        modifier = Modifier
//
//                shape = RoundedCornerShape(8.dp),
//
//                colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f))            .fillMaxSize()@OptIn(ExperimentalMaterial3Api::class)
//
//            ) {
//
//                MyText(            .background(Color.White)
//
//                    title = authStatus.data,
//
//                    color = Color.Green,            .padding(24.dp)@Composableimport androidx.compose.runtime.*
//
//                    size = 14.sp,
//
//                    textAlign = TextAlign.Center,            .verticalScroll(rememberScrollState()),
//
//                    modifier = Modifier.padding(16.dp)
//
//                )        horizontalAlignment = Alignment.CenterHorizontallyfun ForgotPasswordScreen(import androidx.compose.foundation.shape.RoundedCornerShape
//
//            }
//
//        }    ) {
//
//
//
//        if (authStatus is AuthState.Error) {        Spacer(modifier = Modifier.height(60.dp))    navController: NavController,
//
//            Card(
//
//                modifier = Modifier.fillMaxWidth(),
//
//                shape = RoundedCornerShape(8.dp),
//
//                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))        // Title    viewModel: LoginViewModel = hiltViewModel()import androidx.compose.ui.Alignmentimport
//
//            ) {
//
//                MyText(        MyText(
//
//                    title = authStatus.error,
//
//                    color = Color.Red,            title = "استعادة كلمة المرور",) {
//
//                    size = 14.sp,
//
//                    textAlign = TextAlign.Center,            size = 28.sp,
//
//                    modifier = Modifier.padding(16.dp)
//
//                )            fontWeight = FontWeight.Bold,    var email by remember { mutableStateOf("") }androidx.compose.foundation.text.KeyboardOptions
//
//            }
//
//        }            color = mainColor
//
//
//
//        Spacer(modifier = Modifier.height(24.dp))        )    var showOTPBottomSheet by remember { mutableStateOf(false) }
//
//
//
//        TextButton(
//
//            onClick = { navController.popBackStack() }
//
//        ) {        Spacer(modifier = Modifier.height(16.dp))    var isEmailValid by remember { mutableStateOf(true) }import androidx.compose.ui.Modifierimport
//
//            MyText(
//
//                title = "العودة لتسجيل الدخول",
//
//                color = mainColor,
//
//                size = 16.sp        // Subtitle
//
//            )
//
//        }        MyText(
//
//    }
//
//}            title = "أدخل بريدك الإلكتروني وسنرسل لك رمز التحقق",    val authStatus by viewModel.authStatus.collectAsState()androidx.compose.foundation.verticalScroll
//
//            size = 16.sp,
//
//            color = Color.Gray,    val keyboardController = LocalSoftwareKeyboardController.current
//
//            textAlign = TextAlign.Center,
//
//            modifier = Modifier.padding(horizontal = 16.dp)    import androidx.compose.ui.graphics.Colorimport
//
//        )
//
//            // Handle auth status changes
//
//        Spacer(modifier = Modifier.height(40.dp))
//
//            LaunchedEffect(authStatus) {androidx.compose.material3.*
//
//        // Email Input
//
//        AppForm(        when (authStatus) {
//
//            value = email,
//
//            onValueChange = {             is AuthState.Success -> {import androidx.compose.ui.platform.LocalSoftwareKeyboardControllerimport
//
//                email = it
//
//                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()                showOTPBottomSheet = true
//
//            },
//
//            label = "البريد الإلكتروني",                keyboardController?.hide()androidx.compose.runtime.*
//
//            isError = !isEmailValid && email.isNotEmpty(),
//
//            modifier = Modifier.fillMaxWidth()            }
//
//        )
//
//                    is AuthState.Error -> {import androidx.compose.ui.text.font.FontWeightimport
//
//        if (!isEmailValid && email.isNotEmpty()) {
//
//            MyText(                // Error is handled in UI
//
//                title = "يرجى إدخال بريد إلكتروني صالح",
//
//                color = Color.Red,            }androidx.compose.ui.Alignment
//
//                size = 12.sp,
//
//                modifier = Modifier            else -> { /* Loading or Init */ }
//
//                    .fillMaxWidth()
//
//                    .padding(start = 16.dp, top = 4.dp)        }import androidx.compose.ui.text.style.TextAlignimport
//
//            )
//
//        }    }
//
//
//
//        Spacer(modifier = Modifier.height(32.dp))    androidx.compose.ui.Modifier
//
//
//
//        // Send Code Button    Column(
//
//        MyButton(
//
//            title = when (authStatus) {        modifier = Modifierimport androidx.compose.ui.unit.dpimport
//
//                is AuthState.Loading -> "جاري الإرسال..."
//
//                else -> "إرسال رمز التحقق"            .fillMaxSize()
//
//            },
//
//            onClick = {            .background(Color.White)androidx.compose.ui.graphics.Color
//
//                if (email.isNotEmpty() && isEmailValid) {
//
//                    viewModel.forgetPassword(email)            .padding(24.dp)
//
//                }
//
//            },            .verticalScroll(rememberScrollState()),import androidx.compose.ui.unit.spimport
//
//            enable = email.isNotEmpty() && isEmailValid && authStatus !is AuthState.Loading,
//
//            modifier = Modifier.fillMaxWidth()        horizontalAlignment = Alignment.CenterHorizontally
//
//        )
//
//            ) {androidx.compose.ui.input.pointer.pointerInput
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//                Spacer(modifier = Modifier.height(60.dp))
//
//        // Success Message
//
//        if (authStatus is AuthState.Success) {        import androidx.hilt.navigation.compose.hiltViewModelimport
//
//            Card(
//
//                modifier = Modifier.fillMaxWidth(),        // Title
//
//                shape = RoundedCornerShape(8.dp),
//
//                colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f))        MyText(androidx.compose.ui.platform.LocalContext
//
//            ) {
//
//                MyText(            title = "استعادة كلمة المرور",
//
//                    title = authStatus.data,
//
//                    color = Color.Green,            size = 28.sp,import androidx.navigation.NavControllerimport
//
//                    size = 14.sp,
//
//                    textAlign = TextAlign.Center,            fontWeight = FontWeight.Bold,
//
//                    modifier = Modifier.padding(16.dp)
//
//                )            color = mainColorandroidx.compose.ui.platform.LocalFocusManager
//
//            }
//
//        }        )
//
//
//
//        // Error Message        import com.example.myapplication.ui.auth.data.models.AuthStateimport
//
//        if (authStatus is AuthState.Error) {
//
//            Card(        Spacer(modifier = Modifier.height(16.dp))
//
//                modifier = Modifier.fillMaxWidth(),
//
//                shape = RoundedCornerShape(8.dp),        androidx.compose.ui.res.stringResource
//
//                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
//
//            ) {        // Subtitle
//
//                MyText(
//
//                    title = authStatus.error,        MyText(import com.example.myapplication.ui.auth.presentation.manager.LoginViewModelimport
//
//                    color = Color.Red,
//
//                    size = 14.sp,            title = "أدخل بريدك الإلكتروني وسنرسل لك رمز التحقق",
//
//                    textAlign = TextAlign.Center,
//
//                    modifier = Modifier.padding(16.dp)            size = 16.sp,androidx.compose.ui.text.font.FontWeight
//
//                )
//
//            }            color = Color.Gray,
//
//        }
//
//                    textAlign = TextAlign.Center,import com.example.myapplication.ui.component.AppFormimport
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//                    modifier = Modifier.padding(horizontal = 16.dp)
//
//        // Back to Login
//
//        TextButton(        )androidx.compose.ui.text.input.ImeAction
//
//            onClick = { navController.popBackStack() }
//
//        ) {
//
//            MyText(
//
//                title = "العودة لتسجيل الدخول",        Spacer(modifier = Modifier.height(40.dp))import com.example.myapplication.ui.component.MyButtonimport
//
//                color = mainColor,
//
//                size = 16.sp
//
//            )
//
//        }        // Email Inputandroidx.compose.ui.text.input.KeyboardType
//
//    }
//
//}        AppForm(
//
//            value = email,import com.example.myapplication.ui.component.MyTextimport
//
//            onValueChange = {
//
//                email = itandroidx.compose.ui.text.style.TextAlign
//
//                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
//
//            },import com.example.myapplication.ui.component.OTPBottomSheetimport
//
//            label = "البريد الإلكتروني",
//
//            isError = !isEmailValid && email.isNotEmpty(),androidx.compose.ui.unit.dp
//
//            modifier = Modifier.fillMaxWidth()
//
//        )import com.example.myapplication.ui.theme.mainColorimport
//
//
//
//        if (!isEmailValid && email.isNotEmpty()) {androidx.compose.ui.unit.sp
//
//            MyText(
//
//                title = "يرجى إدخال بريد إلكتروني صالح",import androidx.hilt.navigation.compose.hiltViewModel
//
//                color = Color.Red,
//
//                size = 12.sp,@OptIn(ExperimentalMaterial3Api::class) import androidx.lifecycle.compose.collectAsStateWithLifecycle
//
//                modifier = Modifier
//
//                    .fillMaxWidth()@Composableimport androidx.navigation.NavHostController
//
//                    .padding(start = 16.dp, top = 4.dp)
//
//            )fun ForgotPasswordScreen(import com.airbnb.lottie.compose.LottieAnimation
//
//        }
//
//        navController: NavController,import com.airbnb.lottie.compose.LottieCompositionSpec
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        viewModel: LoginViewModel = hiltViewModel()import com.airbnb.lottie.compose.LottieConstants
//
//        // Send Code Button
//
//        MyButton() {
//
//            title = when (authStatus) {    import com . airbnb . lottie . compose . rememberLottieComposition
//
//                is AuthState.Loading -> "جاري الإرسال..."
//
//                else -> "إرسال رمز التحقق"    var email by remember { mutableStateOf("") } import com.example.myapplication.R
//
//            },
//
//            onClick = {    var showOTPBottomSheet by remember { mutableStateOf(false) } import com.example.myapplication.ui.auth.data.models.ForgotPasswordRequest
//
//                if (email.isNotEmpty() && isEmailValid) {
//
//                    viewModel.forgetPassword(email)    var isEmailValid by remember { mutableStateOf(true) } import com.example.myapplication.ui.auth.data.models.LoginState
//
//                }
//
//            },    import com . example . myapplication . ui . auth . data . models . VerifyResetCodeRequest
//
//            enable = email.isNotEmpty() && isEmailValid && authStatus !is AuthState.Loading,
//
//            modifier = Modifier.fillMaxWidth()    val authStatus by viewModel.authStatus.collectAsState() import com.example.myapplication.ui.auth.presentation.manager.LoginViewModel
//
//        )
//
//            val keyboardController =
//
//        Spacer(modifier = Modifier.height(24.dp))        LocalSoftwareKeyboardController.currentimport com . example . myapplication . ui . theme . mainColor
//
//
//
//        // Error Message                import com . example . myapplication . utils . components . AppForm
//
//        if (authStatus is AuthState.Error) {
//
//            Card(                // Handle auth status changesimport com.example.myapplication.utils.components.MyButton
//
//                modifier = Modifier.fillMaxWidth(),
//
//                shape = RoundedCornerShape(8.dp),                LaunchedEffect(authStatus) {
//
//                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))                    import com . example . myapplication . utils . components . MyText
//
//            ) {
//
//                MyText(                            when (authStatus) {
//
//                    title = authStatus.error,                                import com . example . myapplication . utils . components . OTPBottomSheet
//
//                    color = Color.Red,
//
//                    size = 14.sp,                                        is AuthState . Success -> {
//
//                    textAlign = TextAlign.Center,                                    import com . example . myapplication . utils . navigation . LoginRoute
//
//                    modifier = Modifier.padding(16.dp)
//
//                )                                            showOTPBottomSheet =
//
//            }                                        trueimport com . example . myapplication . utils . navigation . RouteRegister
//
//        }
//
//                                                        keyboardController?.hide() import com.example.myapplication.utils.navigation.RouteSuccessScreen
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//                                        }
//
//        // Back to Login
//
//        TextButton(                                is AuthState.Error -> {
//
//            onClick = { navController.popBackStack() }                                    @OptIn(ExperimentalMaterial3Api::class)
//
//        ) {
//
//            MyText(                                    // Error is handled in UI@Composable
//
//                title = "العودة لتسجيل الدخول",
//
//                color = mainColor,                                }
//
//                size = 16.sp
//
//            )                                fun ForgotPasswordScreen(
//
//        }
//
//    }                                    else -> { /* Loading or Init */
//
//                                    } navController : NavHostController,
//
//    // OTP Bottom Sheet
//
//    if (showOTPBottomSheet) {                            } viewModel : LoginViewModel = hiltViewModel ()
//
//        OTPBottomSheet(
//
//            email = email,                }) {
//
//            isVisible = showOTPBottomSheet,
//
//            onDismiss = {        val context = LocalContext.current
//
//                showOTPBottomSheet = false
//
//                viewModel.resetAuthStatus()        Column(val focusManager = LocalFocusManager . current
//
//            },
//
//            onVerifyOTP = { code, newPassword ->                modifier = Modifier
//
//                viewModel.verifyPassword(email, code, newPassword)        var email by remember { mutableStateOf("") }
//
//            },
//
//            authStatus = authStatus            .fillMaxSize()
//
//        )        var showOTPBottomSheet by remember { mutableStateOf(false) }
//
//    }
//
//}            .background(Color.White)
//
//            .padding(24.dp)
//        val forgotPasswordState by viewModel.forgotPassword.collectAsStateWithLifecycle()
//
//            .verticalScroll(rememberScrollState()), val verifyResetCodeState by viewModel.verifyResetCode.collectAsStateWithLifecycle()
//
//        horizontalAlignment = Alignment.CenterHorizontally
//
//        ) {
//
//        Spacer(modifier = Modifier.height(60.dp)) LaunchedEffect (forgotPasswordState) {
//
//            when (forgotPasswordState) {
//
//                // Title            is LoginState.Success -> {
//
//                MyText(
//                    showOTPBottomSheet = true
//
//                            text = "استعادة كلمة المرور",
//            }
//
//            fontSize = 28.sp, else -> {}
//
//            fontWeight = FontWeight.Bold,
//        }
//
//        color = mainColor,
//    }
//
//        textAlign = TextAlign.Center
//
//        )    LaunchedEffect(verifyResetCodeState) {
//
//        when (verifyResetCodeState) {
//
//            Spacer(modifier = Modifier.height(16.dp)) is LoginState.Success -> {
//
//                // Code verified successfully, navigate to reset password screen or success
//
//                // Subtitle                showOTPBottomSheet = false
//
//                MyText(
//                    navController.navigate(RouteSuccessScreen(title = "Reset code verified successfully!")) {
//
//                        text = "أدخل بريدك الإلكتروني وسنرسل لك رمز التحقق", popUpTo(LoginRoute) {
//                        inclusive = true
//                    }
//
//                        fontSize = 16.sp,
//                    }
//
//                            color = Color . Gray,
//            }
//
//            textAlign = TextAlign.Center, is LoginState.Error -> {
//
//                modifier =
//                    Modifier.padding(horizontal = 16.dp) Log . d ("ForgotPasswordScreen", "Error verifying code: ${(verifyResetCodeState as LoginState.Error).error}")
//
//                )
//            }
//
//            else -> {}
//
//            Spacer(modifier = Modifier.height(40.dp))
//        }
//
//    }
//
//        // Email Input
//
//        AppForm(Scaffold { paddingValues ->
//
//            value = email, Box(
//
//            onValueChange = {
//                modifier = Modifier
//
//                email = it.fillMaxSize()
//
//                isEmailValid = android.util.Patterns.EMAIL_ADDRESS
//                    .matcher(it)
//                    .matches()
//                    .background(Color.White)
//
//            }, .padding(paddingValues)
//
//            label = "البريد الإلكتروني", .pointerInput(Unit) {
//
//            isError = !isEmailValid && email.isNotEmpty(), detectTapGestures(onTap = {
//
//            modifier = Modifier.fillMaxWidth() focusManager . clearFocus ()
//
//            )
//        })
//
//        },
//
//            if (!isEmailValid && email.isNotEmpty()) {
//                ) {
//
//                    MyText(
//                        Column(
//
//                            text = "يرجى إدخال بريد إلكتروني صالح", modifier = Modifier
//
//                                    color = Color . Red, . fillMaxSize ()
//
//                                fontSize = 12.sp, . verticalScroll (rememberScrollState())
//
//                    modifier = Modifier.padding(24.dp),
//
//                    .fillMaxWidth() verticalArrangement = Arrangement . Top,
//
//                    .padding(
//                    start = 16.dp,
//                    top = 4.dp
//                ) horizontalAlignment = Alignment . CenterHorizontally
//
//                    )            ) {
//
//                }                // Title
//
//                    MyText(
//
//                        Spacer(modifier = Modifier.height(32.dp)) title = stringResource (R.string.forgot_password),
//
//                        color = mainColor,
//
//                        // Send Code Button                    size = 24.sp,
//
//                        MyButton(
//                            fontWeight = FontWeight.Bold
//
//                                    text =
//                            when (authStatus) {
//                                    )
//
//                                is AuthState.Loading -> "جاري الإرسال..."
//
//                                else -> "إرسال رمز التحقق" Spacer (modifier =
//                                    Modifier.height(10.dp))
//
//                            },
//
//                            onClick = {                // Subtitle
//
//                                if (email.isNotEmpty() && isEmailValid) {
//                                    MyText(
//
//                                        viewModel.forgetPassword(email) title = stringResource (R.string.forgot_password_description),
//
//                                } color = Color . DarkGray,
//
//                            },
//                            size = 14.sp,
//
//                            enabled = email.isNotEmpty() && isEmailValid && authStatus !is AuthState.Loading,
//                        )
//
//                                modifier = Modifier . fillMaxWidth ()
//
//                    ) Spacer (modifier = Modifier.height(30.dp))
//
//
//
//                    Spacer(modifier = Modifier.height(24.dp))                // Email Input using AppForm
//
//                    AppForm(
//
//                        // Error Message                    controller = remember { mutableStateOf(email) },
//
//                        if (authStatus is AuthState.Error) {
//                            onChanged = { email = it },
//
//                            Card(
//                                hintText = stringResource(R.string.enter_email),
//
//                                modifier = Modifier.fillMaxWidth(), type = KeyboardType.Email,
//
//                                shape = RoundedCornerShape(8.dp), textInputAction = ImeAction.Done,
//
//                                colors = CardDefaults.cardColors(
//                                    containerColor = Color.Red.copy(
//                                        alpha = 0.1f
//                                    )
//                                ) onSubmit = { focusManager.clearFocus() }
//
//                            ) {
//                                )
//
//                                MyText(
//
//                                    text = authStatus.error,
//                                    Spacer(modifier = Modifier.height(20.dp))
//
//                                            color = Color . Red,
//
//                                    fontSize = 14.sp,                // Send Button using MyButton
//
//                                    textAlign = TextAlign.Center,
//                                    MyButton(
//
//                                        modifier = Modifier.padding(16.dp) text = stringResource (R.string.send_verification_code),
//
//                                        ) isLoading = forgotPasswordState is LoginState . Loading,
//
//                            } onClick = {
//
//                            }                        if (email.isNotBlank()) {
//
//                                viewModel.forgotPassword(
//
//                                    Spacer(modifier = Modifier.height(24.dp)) ForgotPasswordRequest (email =
//                                        email)
//
//                                )
//
//                                // Back to Login                        }
//
//                                TextButton(
//                            },
//
//                            onClick = { navController.popBackStack() } buttonColor = mainColor,
//
//                            ) {
//                                textColor = Color.White,
//
//                                MyText(
//                                    borderRadius = 8.dp.value
//
//                                            text = "العودة لتسجيل الدخول",
//                                )
//
//                                color = mainColor,
//
//                                fontSize = 16.sp Spacer (modifier = Modifier.height(15.dp))
//
//                                )
//
//                            } TextButton (
//
//                        } onClick = {
//
//                            navController.navigate(LoginRoute) {
//
//                                // OTP Bottom Sheet                            popUpTo(LoginRoute) { inclusive = true }
//
//                                if (showOTPBottomSheet) {
//                                }
//
//                                OTPBottomSheet(
//                            }
//
//                            email = email, ) {
//
//                            isVisible = showOTPBottomSheet, MyText(
//
//                            onDismiss = {
//                                title = stringResource(R.string.back),
//
//                                showOTPBottomSheet = false color = mainColor,
//
//                                viewModel.resetAuthStatus() size = 16.sp,
//
//                            }, fontWeight = FontWeight.Medium
//
//                            onVerifyOTP = { code, newPassword ->
//                                )
//
//                                viewModel.verifyPassword(email, code, newPassword)
//                            }
//
//                        },
//
//                            authStatus =
//                                authStatus//                forgotPasswordState.let { state ->
//
//                            )//                    if (state is LoginState.Error) {
//
//                        }//                        Spacer(modifier = Modifier.height(16.dp))
//
//                }//                        MyText(
////                            title = state.error,
////                            color = Color.Red,
////                            size = 14.sp,
////                        )
////                    }
////                }
//            }
//        }
//    }
//
//    // OTP Bottom Sheet
//    if (showOTPBottomSheet) {
//        OTPBottomSheet(
//            isVisible = showOTPBottomSheet,
//            onDismiss = {
//                showOTPBottomSheet = false
//            },
//            onOTPVerified = { otpCode ->
//                // Verify the reset code using the ViewModel
//                viewModel.verifyResetCode(
//                    VerifyResetCodeRequest(
//                        email = email,
//                        code = otpCode
//                    )
//                )
//            },
//            email = email,
//            isLoading = verifyResetCodeState is LoginState.Loading,
//            errorMessage = verifyResetCodeState.let { state ->
//                if (state is LoginState.Error) state.error else null
//            },
//            onResendCode = {
//                // Resend forgot password request
//                viewModel.forgotPassword(
//                    ForgotPasswordRequest(email = email)
//                )
//            },
//            isResendLoading = forgotPasswordState is LoginState.Loading
//        )
//    }
//}
