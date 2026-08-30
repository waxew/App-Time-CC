package com.bookingsystem.app.ui.customers

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
fun CustomersScreen(navController: NavHostController, viewModel: CustomersViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    var dialog by remember { mutableStateOf(false) }
    MainScaffold(navController, Screen.Customers.route, topBar = { TopAppBar(title = { Text("مشتریان") }) }, floatingActionButton = { FloatingActionButton(onClick = { dialog = true }) { Icon(Icons.Default.Add, null) } }) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            state.errorMessage?.let { item { Text(it, color = MaterialTheme.colorScheme.error) } }
            items(state.customers, key = { it.id }) { c -> Card(Modifier.fillMaxWidth()) { Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) { Column { Text(c.fullName, style = MaterialTheme.typography.titleMedium); Text(c.phone); Text("مراجعه: ${c.visitCount ?: 0}") }; TextButton(onClick = { viewModel.deleteCustomer(c.id) }) { Text("حذف") } } } }
        }
    }
    if (dialog) AddCustomerDialog(onDismiss = { dialog = false }) { n,p -> viewModel.addCustomer(n,p) { dialog = false } }
}

@Composable
private fun AddCustomerDialog(onDismiss: () -> Unit, onSave: (String,String) -> Unit) {
    var name by remember { mutableStateOf("") }; var phone by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("مشتری جدید") }, text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { OutlinedTextField(name,{name=it},label={Text("نام")}); OutlinedTextField(phone,{phone=it},label={Text("موبایل")}) } }, confirmButton = { TextButton(onClick = { onSave(name,phone) }) { Text("ذخیره") } }, dismissButton = { TextButton(onClick = onDismiss) { Text("انصراف") } })
}
