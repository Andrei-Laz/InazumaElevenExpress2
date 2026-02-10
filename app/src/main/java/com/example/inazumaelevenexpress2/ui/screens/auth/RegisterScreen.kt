// ui/screens/auth/RegisterScreen.kt
package com.example.inazumaelevenexpress2.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Register") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("This is a test register screen", modifier = Modifier.padding(bottom = 24.dp))

            Button(
                onClick = onNavigateToHome,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("→ Skip to Main App ←")
            }
        }
    }
}