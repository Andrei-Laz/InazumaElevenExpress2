// ui/screens/auth/RegisterScreen.kt
package com.example.inazumaelevenexpress2.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inazumaelevenexpress2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit
) {
    // Background image as full-screen backdrop
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image with overlay
        Image(
            painter = painterResource(id = R.drawable.inazuma_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Registration form container
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0x88FFFFFF),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0x33FFFFFF),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(24.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    // Title
                    Text(
                        text = stringResource(R.string.create_account),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    )

                    // Username field
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Username", color = Color(0xFF555555)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF0078FF),
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            focusedContainerColor = Color(0xFFFAFAFA),
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            cursorColor = Color(0xFF0078FF)
                        )
                    )

                    // Email field
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Email", color = Color(0xFF555555)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF0078FF),
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            focusedContainerColor = Color(0xFFFAFAFA),
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            cursorColor = Color(0xFF0078FF)
                        )
                    )

                    // Password field
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Password", color = Color(0xFF555555)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF0078FF),
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            focusedContainerColor = Color(0xFFFAFAFA),
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            cursorColor = Color(0xFF0078FF)
                        )
                    )

                    // Register button
                    Button(
                        onClick = onNavigateToLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0078FF),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.register),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
    }
}