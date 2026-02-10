package com.example.inazumaelevenexpress2.ui

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.inazumaelevenexpress2.data.local.UserPreferences
import com.example.inazumaelevenexpress2.ui.navigation.AppNavGraph

@Composable
fun InazumaElevenExpressApp(context: Context) {
    // Create UserPreferences instance (in production, inject via Hilt)
    val userPreferences = remember { UserPreferences(context) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppNavGraph()
    }
}