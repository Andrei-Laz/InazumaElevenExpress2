package com.example.inazumaelevenexpress2.ui.screens.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inazumaelevenexpress2.R
import com.example.inazumaelevenexpress2.model.InazumaCharacter
import com.example.inazumaelevenexpress2.ui.theme.DarkOrange
import com.example.inazumaelevenexpress2.ui.theme.ElementColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    character: InazumaCharacter,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        character.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkOrange
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background with gradient overlay
            Image(
                painter = painterResource(id = R.drawable.inazuma_background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient overlay for text readability
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

            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Character portrait
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .shadow(12.dp, CircleShape)
                        .clip(CircleShape)
                        .border(4.dp, ElementColor(character.element), CircleShape)
                ) {
                    AsyncImage(
                        model = R.drawable.default_image,
                        contentDescription = character.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Element badge overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(ElementColor(character.element))
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = character.element.name.first().toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nickname display
                if (character.nickname.isNotBlank()) {
                    Text(
                        text = "\"${character.nickname}\"",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700) // Gold for nickname
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Position badge
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(DarkOrange, Color(0xFF8B0000))
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = character.position.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Stats Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x882C2C2C)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "PLAYER STATS",
                            color = Color(0xFFFFD700),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Grid (2 columns)
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Left column - Offensive stats
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                StatRow("Kick", character.kick, Color(0xFFFF6B6B)) // Red - Power
                                StatRow("Dribble", character.dribble, Color(0xFF4ECDC4)) // Teal - Technique
                                StatRow("Technique", character.technique, Color(0xFFFFA500)) // Orange - Skill
                                StatRow("Speed", character.speed, Color(0xFF7ED6DF)) // Blue - Agility
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            // Right column - Defensive/Physical stats
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                StatRow("Block", character.block, Color(0xFF96CEB4)) // Green - Defense
                                StatRow("Catch", character.catch, Color(0xFF6C5CE7)) // Purple - Goalkeeping
                                StatRow("Stamina", character.stamina, Color(0xFFFFD93D)) // Yellow - Endurance
                                StatRow("Luck", character.luck, Color(0xFFFF9FF3)) // Pink - Special
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Hissatsus Section (Placeholder for now)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x882C2C2C)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.lightning_bolt),
                                contentDescription = "Hissatsu",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "HISSATSUS",
                                color = Color(0xFFFFD700),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Special techniques will appear here when assigned to this character",
                            color = Color.LightGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Placeholder for future hissatsu chips
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                                .background(Color(0x33FFFFFF), RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: Int, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Stat label
        Text(
            text = label,
            color = Color.LightGray,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        // Stat value with bar visualization
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1.5f)
        ) {
            // Value text
            Text(
                text = value.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.width(30.dp)
            )

            // Progress bar
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0x66FFFFFF))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width((value * 2.5f).dp) // Scale to max ~250dp (for value 100)
                        .clip(RoundedCornerShape(4.dp, if (value == 100) 4.dp else 0.dp, if (value == 100) 4.dp else 0.dp, 4.dp))
                        .background(color)
                )
            }
        }
    }
}