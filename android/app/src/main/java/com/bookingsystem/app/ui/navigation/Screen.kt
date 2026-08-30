package com.bookingsystem.app.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Appointments : Screen("appointments")
    object AddAppointment : Screen("add_appointment")
    object Customers : Screen("customers")
    object Services : Screen("services")
    object Staff : Screen("staff")
}
