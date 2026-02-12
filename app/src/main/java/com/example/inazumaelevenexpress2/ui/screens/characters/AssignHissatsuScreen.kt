// ui/screens/characters/AssignHissatsuScreen.kt
package com.example.inazumaelevenexpress2.ui.screens.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inazumaelevenexpress2.R
import com.example.inazumaelevenexpress2.model.Hissatsu
import com.example.inazumaelevenexpress2.ui.screens.hissatsus.HissatsusViewModel
import com.example.inazumaelevenexpress2.ui.theme.ElementColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignHissatsuScreen(
    characterId: Int,
    characterName: String,
    navController: NavController
) {
    // ✅ CORRECT: Get ViewModels INSIDE composable using factory pattern
    val characterDetailsViewModel: CharacterDetailsViewModel = viewModel(factory = CharacterDetailsViewModel.Factory)
    val hissatsusViewModel: HissatsusViewModel = viewModel(factory = HissatsusViewModel.Factory)

    // Load assigned hissatsus when screen opens
    LaunchedEffect(characterId) {
        characterDetailsViewModel.loadAssignedHissatsus(characterId)
    }

    // ✅ FIX 1: For StateFlow (CharacterDetailsViewModel), use collectAsState()
    val assignedUiState by characterDetailsViewModel.uiState.collectAsState()

    // ✅ FIX 2: For mutableStateOf (HissatsusViewModel), access DIRECTLY (no collectAsState needed)
    val hissatsusUiState = hissatsusViewModel.hissatsusUiState

    // Extract assigned hissatsu IDs for filtering
    val assignedIds = when (assignedUiState) {
        is CharacterDetailsUiState.Success -> (assignedUiState as CharacterDetailsUiState.Success).assignedHissatsus.mapNotNull { it.hissatsuId }
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Assign to $characterName",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF6B6B)  // Fire orange-red
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Background with gradient overlay
            Image(
                painter = painterResource(id = R.drawable.inazuma_background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x99000000),
                                Color(0x66000000)
                            )
                        )
                    )
            )

            // Content based on state
            when (hissatsusUiState) {
                // ✅ FIX 3: Proper when expression for sealed interface (no smart cast needed)
                is com.example.inazumaelevenexpress2.ui.screens.hissatsus.HissatsusUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            strokeWidth = 4.dp,
                            color = Color(0xFFFFD700)
                        )
                    }
                }

                com.example.inazumaelevenexpress2.ui.screens.hissatsus.HissatsusUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "❌ Failed to load hissatsus",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Tap to retry",
                                color = Color.LightGray,
                                fontSize = 14.sp
                            )
                            Button(
                                onClick = { hissatsusViewModel.getHissatsus() },
                                shape = RoundedCornerShape(24.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF6B6B),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Retry Loading", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                is com.example.inazumaelevenexpress2.ui.screens.hissatsus.HissatsusUiState.Success -> {
                    val availableHissatsus = hissatsusUiState.hissatsus
                        .filter { it.hissatsuId !in assignedIds }
                        .sortedByDescending { it.power }

                    if (availableHissatsus.isEmpty()) {
                        // All hissatsus already assigned
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.lightning_bolt),
                                    contentDescription = "All assigned",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    text = "All hissatsus assigned!",
                                    color = Color(0xFFFFD700),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "This character has all available hissatsus",
                                    color = Color.LightGray,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 32.dp)
                                )
                                Button(
                                    onClick = { navController.popBackStack() },
                                    shape = RoundedCornerShape(24.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF0078FF),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text("Back to Character", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    } else {
                        // Show list of available hissatsus
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                Text(
                                    text = "Available Hissatsus (${availableHissatsus.size})",
                                    color = Color(0xFFFFD700),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Tap any hissatsu to assign it to $characterName",
                                    color = Color.LightGray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                            }

                            items(
                                items = availableHissatsus,
                                key = { it.hissatsuId ?: it.name.hashCode() }
                            ) { hissatsu ->
                                HissatsuAssignmentItem(
                                    hissatsu = hissatsu,
                                    onClick = {
                                        characterDetailsViewModel.assignHissatsu(
                                            characterId = characterId,
                                            hissatsuId = hissatsu.hissatsuId!!
                                        )
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HissatsuAssignmentItem(
    hissatsu: Hissatsu,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x22FFFFFF)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Element badge
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ElementColor(hissatsu.element))
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = hissatsu.element.name.first().toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                    Text(
                        text = hissatsu.power.toString(),
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Hissatsu info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = hissatsu.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Type: ${hissatsu.type.name.lowercase().replaceFirstChar { it.uppercase() }} · Power: ${hissatsu.power}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xCCFFFFFF)
                )

                if (hissatsu.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = hissatsu.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Assign button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFD700))
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material.icons.Icons.Default.Add
            }
        }
    }
}