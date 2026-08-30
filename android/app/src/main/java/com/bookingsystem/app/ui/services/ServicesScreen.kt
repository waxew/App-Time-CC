package com.bookingsystem.app.ui.services

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bookingsystem.app.ui.navigation.MainScaffold
import com.bookingsystem.app.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavHostController, viewModel: ServicesViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState(); var dialog by remember { mutableStateOf(false) }
    MainScaffold(navController, Screen.Services.route, topBar = { TopAppBar(title = { Text("خدمات") }) }, floatingActionButton = { FloatingActionButton(onClick = { dialog = true }) { Icon(Icons.Default.Add, null) } }) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            state.errorMessage?.let { item { Text(it, color = MaterialTheme.colorScheme.error) } }
            items(state.services, key = { it.id }) { s -> Card(Modifier.fillMaxWidth()) { Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) { Column { Text(s.name, style = MaterialTheme.typography.titleMedium); Text("${s.durationMinutes} دقیقه"); s.price?.let { Text("$it") } }; TextButton(onClick = { viewModel.deleteService(s.id) }) { Text("حذف") } } } }
        }
    }
    if (dialog) AddServiceDialog(onDismiss = { dialog = false }) { n,d,p -> viewModel.addService(n,d,p) { dialog = false } }
}

@Composable
private fun AddServiceDialog(onDismiss: () -> Unit, onSave: (String,Int,Double?) -> Unit) {
    var name by remember { mutableStateOf("") }; var duration by remember { mutableStateOf("30") }; var price by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("خدمت جدید") }, text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { OutlinedTextField(name,{name=it},label={Text("نام")}); OutlinedTextField(duration,{duration=it},label={Text("مدت (دقیقه)")}); OutlinedTextField(price,{price=it},label={Text("قیمت")}) } }, confirmButton = { TextButton(onClick = { onSave(name,duration.toIntOrNull() ?: 30,price.toDoubleOrNull()) }) { Text("ذخیره") } }, dismissButton = { TextButton(onClick = onDismiss) { Text("انصراف") } })
}
