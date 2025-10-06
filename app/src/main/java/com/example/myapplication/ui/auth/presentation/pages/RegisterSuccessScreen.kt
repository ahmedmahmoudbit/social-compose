package com.example.myapplication.ui.auth.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myapplication.R
import com.example.myapplication.ui.theme.mainColor
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.components.MyTopAppBar
import com.example.myapplication.utils.components.colors.mainColor
import com.example.myapplication.utils.navigation.RouteSuccessScreen

@Composable
fun RegisterSuccessScreen(
    navController: NavHostController = rememberNavController(),
    it: NavBackStackEntry
) {
    val args = it.toRoute<RouteSuccessScreen>()
    val lottieRegister by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.register)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.verify),
                navController,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = lottieRegister,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.height(300.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = args.title,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))

                TextButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    MyText(
                        title = stringResource(R.string.i_ll_check_it_out),
                        color = MaterialTheme.colorScheme.mainColor,
                        size = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}




