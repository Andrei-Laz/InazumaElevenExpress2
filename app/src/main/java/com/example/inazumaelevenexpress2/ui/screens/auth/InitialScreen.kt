package com.example.inazumaelevenexpress2.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.inazumaelevenexpress2.R

@Composable
fun InitialScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App logo (use your actual logo drawable)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Register")
        }
    }
}
