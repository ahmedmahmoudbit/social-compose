package com.example.myapplication.ui.home.domain.uc

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R

val navigationItems = listOf(
    NavigationItem(R.string.home_screen, Icons.Default.Home),
    NavigationItem(R.string.add_post, Icons.Default.AutoFixHigh),
    NavigationItem(R.string.users, Icons.Default.People),
    NavigationItem(R.string.profile, Icons.Default.Person)
)

data class NavigationItem(
    val title: Int,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)