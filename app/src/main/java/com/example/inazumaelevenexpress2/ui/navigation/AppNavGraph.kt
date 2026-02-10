// ui/navigation/AppNavGraph.kt
package com.example.inazumaelevenexpress2.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inazumaelevenexpress2.ui.screens.auth.InitialScreen
import com.example.inazumaelevenexpress2.ui.screens.auth.LoginScreen
import com.example.inazumaelevenexpress2.ui.screens.auth.RegisterScreen
import com.example.inazumaelevenexpress2.ui.screens.characters.CharactersScreen
import com.example.inazumaelevenexpress2.ui.screens.characters.InazumaCharactersViewModel
import com.example.inazumaelevenexpress2.ui.screens.hissatsus.HissatsusScreen
import com.example.inazumaelevenexpress2.ui.screens.hissatsus.HissatsusViewModel
import com.example.inazumaelevenexpress2.ui.screens.home.HomeScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Initial.route
    ) {
        composable(route = Screen.Initial.route) {
            InitialScreen(
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCharacters = { navController.navigate(Screen.Characters.route) },
                onNavigateToHissatsus = { navController.navigate(Screen.Hissatsus.route) },
                onLogout = {
                    navController.navigate(Screen.Initial.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Characters.route) {
            val viewModel: InazumaCharactersViewModel = viewModel(
                factory = InazumaCharactersViewModel.Factory
            )

            CharactersScreen(
                uiState = viewModel.inazumaCharactersUiState,
                retryAction = viewModel::getCharacters,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = Screen.Hissatsus.route) {
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