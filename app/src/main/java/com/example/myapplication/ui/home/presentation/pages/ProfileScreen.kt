package com.example.myapplication.ui.home.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.auth.data.models.AuthState
import com.example.myapplication.ui.home.presentation.manager.HomeViewModel
import com.example.myapplication.ui.home.presentation.manager.ProfileViewModel
import com.example.myapplication.utils.components.MyButton
import com.example.myapplication.utils.navigation.AppViewModel
import com.example.myapplication.utils.navigation.LoginRoute

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        // Profile Image
//        AsyncImage(
//            model = "https://via.placeholder.com/150",
//            contentDescription = "Profile Picture",
//            modifier = Modifier
//                .size(120.dp)
//                .clip(CircleShape),
//            contentScale = ContentScale.Crop
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Name
//        Text(
//            text = "أحمد محمود",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.onSurface
//        )
//
//        Text(
//            text = "@ahmed_mahmoud",
//            fontSize = 16.sp,
//            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Profile Info Cards
//        ProfileInfoCard(
//            icon = Icons.Default.Email,
//            title = "البريد الإلكتروني",
//            value = "ahmed@example.com"
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        ProfileInfoCard(
//            icon = Icons.Default.Phone,
//            title = "رقم الهاتف",
//            value = "+966 50 123 4567"
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        ProfileInfoCard(
//            icon = Icons.Default.Person,
//            title = "نبذة شخصية",
//            value = "مطور تطبيقات موبايل بخبرة 5 سنوات"
//        )

        Spacer(modifier = Modifier.height(24.dp))
//        Button(
//            onClick = { /* TODO: Handle edit profile */ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Edit,
//                contentDescription = "Edit",
//                modifier = Modifier.size(20.dp)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                text = "تعديل الملف الشخصي",
//                fontSize = 16.sp
//            )
//        }
        MyButton(
            text = stringResource(R.string.logOut),
            onClick = { viewModel.logOut() },
        )
    }
}

@Composable
private fun ProfileInfoCard(
    icon: ImageVector,
    title: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}