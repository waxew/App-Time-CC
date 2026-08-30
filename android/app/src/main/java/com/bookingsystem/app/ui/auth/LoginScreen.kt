package com.bookingsystem.app.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "ورود به پنل کسب‌وکار", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.size(24.dp))
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("شماره موبایل") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.size(12.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("رمز عبور") }, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.size(8.dp))
            uiState.errorMessage?.let { message -> Text(text = message, color = MaterialTheme.colorScheme.error); Spacer(Modifier.size(8.dp)) }
            Button(onClick = { viewModel.login(phone, password, onLoginSuccess) }, enabled = !uiState.isLoading, modifier = Modifier.fillMaxWidth()) {
                if (uiState.isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp)) else Text("ورود")
            }
            Spacer(Modifier.size(12.dp))
            TextButton(onClick = onNavigateToRegister, modifier = Modifier.fillMaxWidth()) { Text("حساب ندارید؟ ثبت‌نام کسب‌وکار جدید") }
        }
    }
}
