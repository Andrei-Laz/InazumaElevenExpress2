
package com.example.inazumaelevenexpress2.ui.screens.main

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inazumaelevenexpress2.R
import com.example.inazumaelevenexpress2.ui.navigation.Screen
import com.example.inazumaelevenexpress2.ui.screens.characters.CharactersScreen
import com.example.inazumaelevenexpress2.ui.screens.characters.InazumaCharactersViewModel
import com.example.inazumaelevenexpress2.ui.screens.hissatsus.HissatsusScreen
import com.example.inazumaelevenexpress2.ui.screens.hissatsus.HissatsusViewModel
import com.example.inazumaelevenexpress2.ui.screens.home.HomeScreen
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.Settings.route)
                            scope.launch { drawerState.close() }
                        }
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Account") },
                        label = { Text("Account") },
                        selected = false,
                        onClick = {
                            navController.navigate(Screen.Account.route)
                            scope.launch { drawerState.close() }
                        }
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },
                        label = { Text("Logout") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Initial.route) {
                                popUpTo(Screen.Main.route) { inclusive = true }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .offset {
                                IntOffset(
                                    offset.value.x.roundToInt(),
                                    offset.value.y.roundToInt()
                                )
                            }
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragEnd = {
                                        scope.launch {
                                            offset.animateTo(
                                                targetValue = Offset.Zero,
                                                animationSpec = spring(
                                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                                    stiffness = Spring.StiffnessLow
                                                )
                                            )
                                        }
                                    }
                                ) { change, dragAmount ->
                                    change.consume()
                                    scope.launch {
                                        offset.snapTo(offset.value + dragAmount)
                                    }
                                }
                            }
                    ) {
                        val infiniteTransition = rememberInfiniteTransition("lightning-glow")
                        val animatedColor by infiniteTransition.animateColor(
                            initialValue = Color(0xFFFDD835), // Lighter gold
                            targetValue = Color(0xFFF57F17),  // Darker gold
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "lightning-color"
                        )

                        // Background glow
                        Image(
                            painter = painterResource(id = R.drawable.lightning_bolt),
                            contentDescription = null,
                            modifier = Modifier
                                .matchParentSize()
                                .blur(8.dp),
                            colorFilter = ColorFilter.tint(animatedColor)
                        )

                        // Foreground image
                        Image(
                            painter = painterResource(id = R.drawable.lightning_bolt),
                            contentDescription = "Lightning Bolt",
                            modifier = Modifier.matchParentSize()
                        )
                    }
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.inazuma_background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            navigationIconContentColor = Color.White
                        ),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            label = { Text("Home") },
                            selected = pagerState.currentPage == 0,
                            onClick = { scope.launch { pagerState.animateScrollToPage(0) } }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.List, contentDescription = "Characters") },
                            label = { Text("Characters") },
                            selected = pagerState.currentPage == 1,
                            onClick = { scope.launch { pagerState.animateScrollToPage(1) } }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Star, contentDescription = "Hissatsus") },
                            label = { Text("Hissatsus") },
                            selected = pagerState.currentPage == 2,
                            onClick = { scope.launch { pagerState.animateScrollToPage(2) } }
                        )
                    }
                },
                containerColor = Color.Transparent
            ) { innerPadding ->
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.padding(innerPadding)
                ) { page ->
                    when (page) {
                        0 -> HomeScreen()
                        1 -> {
                            val viewModel: InazumaCharactersViewModel = viewModel(
                                factory = InazumaCharactersViewModel.Factory
                            )
                            CharactersScreen(
                                uiState = viewModel.inazumaCharactersUiState,
                                retryAction = viewModel::getCharacters,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        2 -> {
                            val viewModel: HissatsusViewModel = viewModel(
                                factory = HissatsusViewModel.Factory
                            )
                            HissatsusScreen(
                                uiState = viewModel.hissatsusUiState,
                                retryAction = viewModel::getHissatsus,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
