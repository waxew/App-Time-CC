package com.bookingsystem.app.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.bookingsystem.app.data.repository.AuthRepository
import com.bookingsystem.app.ui.appointments.AddAppointmentScreen
import com.bookingsystem.app.ui.appointments.AppointmentsScreen
import com.bookingsystem.app.ui.auth.LoginScreen
import com.bookingsystem.app.ui.auth.RegisterScreen
import com.bookingsystem.app.ui.customers.CustomersScreen
import com.bookingsystem.app.ui.services.ServicesScreen
import com.bookingsystem.app.ui.staff.StaffScreen
import kotlinx.coroutines.launch

@Composable
fun BookingNavGraph(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val authRepository = remember { AuthRepository(context) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (authRepository.isLoggedIn()) {
            navController.navigate(Screen.Appointments.route) { popUpTo(Screen.Login.route) { inclusive = true } }
        }
    }

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = { navController.navigate(Screen.Appointments.route) { popUpTo(Screen.Login.route) { inclusive = true } } }, onNavigateToRegister = { navController.navigate(Screen.Register.route) })
        }
        composable(Screen.Register.route) {
            RegisterScreen(onRegisterSuccess = { navController.navigate(Screen.Appointments.route) { popUpTo(Screen.Login.route) { inclusive = true } } }, onNavigateToLogin = { navController.popBackStack() })
        }
        composable(Screen.Appointments.route) {
            AppointmentsScreen(navController = navController, onLogout = {
                coroutineScope.launch { authRepository.logout() }
                navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
            })
        }
        composable(Screen.AddAppointment.route) { AddAppointmentScreen(onDone = { navController.popBackStack() }) }
        composable(Screen.Customers.route) { CustomersScreen(navController = navController) }
        composable(Screen.Services.route) { ServicesScreen(navController = navController) }
        composable(Screen.Staff.route) { StaffScreen(navController = navController) }
    }
}
