package com.bookingsystem.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bookingsystem.app.ui.navigation.BookingNavGraph
import com.bookingsystem.app.ui.theme.BookingSystemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookingSystemTheme {
                BookingNavGraph()
            }
        }
    }
}
