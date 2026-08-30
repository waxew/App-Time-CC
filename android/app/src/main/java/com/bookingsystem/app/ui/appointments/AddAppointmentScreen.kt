package com.bookingsystem.app.ui.appointments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentScreen(onDone: () -> Unit, viewModel: AddAppointmentViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    var serviceId by remember { mutableStateOf<String?>(null) }
    var customerId by remember { mutableStateOf<String?>(null) }
    var staffId by remember { mutableStateOf<String?>(null) }
    var startTime by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("ثبت نوبت جدید") }, navigationIcon = { IconButton(onClick = onDone) { Icon(Icons.Default.ArrowBack, null) } }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (state.isLoading) CircularProgressIndicator()
            SimpleSelector("خدمت", state.services.map { it.id to it.name }, serviceId) { serviceId = it }
            SimpleSelector("مشتری", state.customers.map { it.id to it.fullName }, customerId) { customerId = it }
            SimpleSelector("پرسنل", state.staff.map { it.id to it.fullName }, staffId) { staffId = it }
            OutlinedTextField(value = startTime, onValueChange = { startTime = it }, modifier = Modifier.fillMaxWidth(), label = { Text("تاریخ و ساعت ISO") }, placeholder = { Text("2026-08-31T10:00:00+03:30") })
            OutlinedTextField(value = notes, onValueChange = { notes = it }, modifier = Modifier.fillMaxWidth(), label = { Text("توضیحات") })
            state.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Button(onClick = { viewModel.submit(customerId, serviceId, staffId, startTime, notes, onDone) }, enabled = !state.isSaving, modifier = Modifier.fillMaxWidth()) { Text("ثبت نوبت") }
        }
    }
}

@Composable
private fun SimpleSelector(label: String, options: List<Pair<String,String>>, selected: String?, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxWidth()) {
        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) { Text(options.firstOrNull { it.first == selected }?.second ?: label) }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { (id, name) -> DropdownMenuItem(text = { Text(name) }, onClick = { onSelect(id); expanded = false }) }
        }
    }
}
