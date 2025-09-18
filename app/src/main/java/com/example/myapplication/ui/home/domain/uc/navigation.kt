package com.example.myapplication.ui.home.domain.uc

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person

val navigationItems = listOf(
    NavigationItem("الرئيسية", Icons.Default.Home),
    NavigationItem("الإشعارات", Icons.Default.Notifications),
    NavigationItem("المستخدمون", Icons.Default.People),
    NavigationItem("الملف الشخصي", Icons.Default.Person)
)

data class NavigationItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)