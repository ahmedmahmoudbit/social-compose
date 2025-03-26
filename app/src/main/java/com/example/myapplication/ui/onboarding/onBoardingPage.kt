package com.example.myapplication.ui.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.mainColor
import com.example.myapplication.ui.theme.scaffoldColor
import com.example.myapplication.utils.navigation.LoginRoute
import kotlinx.coroutines.launch

val pages = listOf(
    OnboardingPage("Welcome to", "Socially", R.drawable.beach_svgrepo_com),
    OnboardingPage("Connect with", "Friends", R.drawable.ic_trees),
    OnboardingPage("Enjoy", "Your Journey", R.drawable.ic_airplane)
)

@Composable
fun OnboardingScreen(navController: NavHostController) {

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        modifier = Modifier.fillMaxSize().systemBarsPadding()
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(scaffoldColor)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color(0xFFDDEFF2),
                    radius = size.width * 0.4f,
                    center = center.copy(y = size.height * 0f),
                    style = Stroke(width = 50f, pathEffect = PathEffect.cornerPathEffect(50f))
                )
            }

            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                HorizontalPager(
//                    modifier = Modifier.fillMaxWidth(),

                    state = pagerState) { page ->
                    OnboardingPageView(pages[page])
                }
                Spacer(modifier = Modifier.height(20.dp))
                DotsIndicator(currentPage = pagerState.currentPage, totalPages = pages.size)

            }



            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset((-60).dp, (30).dp)
                    .rotate(-45f)
                    .size(220.dp, 220.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(mainColor)
                    .padding(3.dp)
                    .border(
                        width = 2.dp,
                        color = scaffoldColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable {
                        coroutineScope.launch {
                            if (pagerState.currentPage < pages.lastIndex) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                navController.navigate(LoginRoute)
                            }
                        }
                    }
                    .shadow(8.dp, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .rotate(45f)
                        .padding(start = 35.dp),
                    text = if (pagerState.currentPage == pages.lastIndex) "Finish" else "Next →",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }

}
@Composable
fun DotsIndicator(currentPage: Int, totalPages: Int) {
    Row(horizontalArrangement = Arrangement.Center) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier.padding(start=5.dp)
                    .size(if (index == currentPage) 15.dp else 15.dp)
                    .clip(CircleShape)
                    .background(if (index == currentPage) Color.Black else Color.Gray)
            )
        }
    }
}

@Composable
fun OnboardingPageView(page: OnboardingPage,) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier.size(320.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = page.title, fontSize = 24.sp, color = Color.Gray)

        Text(
            text = page.subtitle,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

    }
}

data class OnboardingPage(val title: String, val subtitle: String, val imageRes: Int)
