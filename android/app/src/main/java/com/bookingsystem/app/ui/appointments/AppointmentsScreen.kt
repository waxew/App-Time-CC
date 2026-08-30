package com.bookingsystem.app.ui.appointments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bookingsystem.app.ui.navigation.MainScaffold
import com.bookingsystem.app.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(navController: NavHostController, onLogout: () -> Unit, viewModel: AppointmentsViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    MainScaffold(
        navController = navController,
        currentRoute = Screen.Appointments.route,
        topBar = { TopAppBar(title = { Text("نوبت‌ها") }, actions = { IconButton(onClick = onLogout) { Icon(Icons.Default.Logout, null) } }) },
        floatingActionButton = { FloatingActionButton(onClick = { navController.navigate(Screen.AddAppointment.route) }) { Icon(Icons.Default.Add, null) } }
    ) { padding ->
        when {
            state.isLoading -> Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            state.appointments.isEmpty() -> Box(Modifier.fillMaxSize().padding(padding).padding(24.dp), contentAlignment = Alignment.Center) { Text(state.errorMessage ?: "هنوز نوبتی ثبت نشده") }
            else -> LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(state.appointments, key = { it.id }) { item ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text(item.customerName ?: "بدون مشتری", style = MaterialTheme.typography.titleMedium)
                            Text(item.serviceName ?: "خدمت")
                            Text(item.startTime)
                            Text("وضعیت: ${item.status}")
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TextButton(onClick = { viewModel.updateStatus(item.id, "confirmed") }) { Text("تایید") }
                                TextButton(onClick = { viewModel.updateStatus(item.id, "done") }) { Text("انجام شد") }
                                TextButton(onClick = { viewModel.deleteAppointment(item.id) }) { Text("حذف") }
                            }
                        }
                    }
                }
            }
        }
    }
}
