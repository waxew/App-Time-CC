package com.bookingsystem.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

private data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)
private val bottomNavItems = listOf(
    BottomNavItem(Screen.Appointments.route, "نوبت‌ها", Icons.Filled.CalendarMonth),
    BottomNavItem(Screen.Customers.route, "مشتریان", Icons.Filled.People),
    BottomNavItem(Screen.Services.route, "خدمات", Icons.Filled.Spa),
    BottomNavItem(Screen.Staff.route, "پرسنل", Icons.Filled.Groups)
)

@Composable
fun MainScaffold(
    navController: NavHostController,
    currentRoute: String,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(topBar = topBar, bottomBar = {
        NavigationBar {
            bottomNavItems.forEach { item ->
                NavigationBarItem(selected = currentRoute == item.route, onClick = {
                    if (currentRoute != item.route) navController.navigate(item.route) {
                        popUpTo(Screen.Appointments.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = { Icon(item.icon, contentDescription = item.label) }, label = { Text(item.label) })
            }
        }
    }, floatingActionButton = floatingActionButton) { padding -> content(padding) }
}
