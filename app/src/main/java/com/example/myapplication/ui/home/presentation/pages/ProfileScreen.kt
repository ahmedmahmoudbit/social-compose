package com.example.myapplication.ui.home.presentation.pages

import android.widget.Toast
import kotlinx.serialization.encodeToString
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.DataState
import com.example.myapplication.ui.home.presentation.manager.ProfileViewModel
import com.example.myapplication.ui.theme.mainColor
import com.example.myapplication.ui.users.data.model.Users
import com.example.myapplication.utils.components.ImageNetwork
import com.example.myapplication.utils.components.MyButton
import com.example.myapplication.utils.components.MyText
import com.example.myapplication.utils.components.colors.cardColorBackground
import com.example.myapplication.utils.components.page.ErrorPage
import com.example.myapplication.utils.components.page.LoadingPage
import com.example.myapplication.utils.navigation.ChangePasswordRoute
import com.example.myapplication.utils.navigation.ChangeProfileRoute
import com.example.myapplication.utils.navigation.LoginRoute
import com.example.myapplication.utils.navigation.ThemeSettingsRoute
import kotlinx.serialization.json.Json
import verticalSpace

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>()
) {

    val response = viewModel.user.collectAsState()
    val responseDelete = viewModel.message.collectAsState()
    val status = response.value
    val statusDelete = responseDelete.value

    LaunchedEffect(Unit) {
        viewModel.getProfile()
    }

    LaunchedEffect(statusDelete) {
        when (statusDelete) {
            DataState.Init -> {}
            DataState.Loading -> {}
            is DataState.Error -> {}
            is DataState.Success -> {
                Toast.makeText(
                    navController.context,
                    statusDelete.data.message,
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(LoginRoute) {
                    popUpTo(0)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (status) {
            is DataState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ErrorPage(
                            message = status.error,
                            onRetry = { viewModel.getProfile() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )

                        MyButton(
                            text = stringResource(R.string.logOut),
                            onClick = { viewModel.logOut() },
                        )
                    }
                }
            }

            is DataState.Loading -> LoadingPage()
            is DataState.Init -> LoadingPage()
            is DataState.Success -> {
                val data = status.data.data
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    ImageNetwork(
                        image = data.avatar,
                        size = 150.dp,
                        onClick = { navController.navigate(ThemeSettingsRoute) }
                    )
                    8.verticalSpace
                    Divider(modifier = Modifier.fillMaxWidth())
                    8.verticalSpace
                    InfoItem(
                        txt = data.username,
                        icon = Icons.Default.Person
                    )
                    InfoItem(
                        txt = data.fullName,
                        icon = Icons.Default.PersonPin
                    )
                    InfoItem(
                        txt = data.email,
                        icon = Icons.Default.Email
                    )
                    InfoItem(
                        txt = data.phone!!,
                        icon = Icons.Default.Phone
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    MyButton(
                        text = stringResource(R.string.change_profile),
                        onClick = {
                            val json = Json.encodeToString<Users>(data)
                            navController.navigate(ChangeProfileRoute(json))
                        },
                    )
                    MyButton(
                        text = stringResource(R.string.change_password),
                        onClick = { navController.navigate(ChangePasswordRoute) },
                    )
                    MyButton(
                        text = stringResource(R.string.logOut),
                        onClick = { viewModel.logOut() },
                    )
                    MyButton(
                        buttonColor = Color(0xFFAF352D),
                        text = stringResource(R.string.delete_account),
                        isLoading = viewModel.message.collectAsState().value is DataState.Loading,
                        onClick = { viewModel.deleteAccount() },
                    )
                }
            }
        }

    }
}

@Composable
private fun InfoItem(
    txt: String,
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = txt,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.width(10.dp))
        Icon(
            imageVector = icon,
            contentDescription = "Home Icon",
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.cardColorBackground)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}