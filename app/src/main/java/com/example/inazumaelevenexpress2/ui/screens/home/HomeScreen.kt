// ui/screens/home/HomeScreen.kt
package com.example.inazumaelevenexpress2.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToCharacters: () -> Unit,
    onNavigateToHissatsus: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Inazuma Eleven Express!",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onNavigateToCharacters,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Characters")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToHissatsus,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Hissatsus")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onErrorContainer)
        }
    }
}