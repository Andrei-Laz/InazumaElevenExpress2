// ui/navigation/AppNavGraph.kt
package com.example.inazumaelevenexpress2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inazumaelevenexpress2.ui.screens.account.AccountScreen
import com.example.inazumaelevenexpress2.ui.screens.auth.InitialScreen
import com.example.inazumaelevenexpress2.ui.screens.auth.LoginScreen
import com.example.inazumaelevenexpress2.ui.screens.auth.RegisterScreen
import com.example.inazumaelevenexpress2.ui.screens.characters.AssignHissatsuScreen
import com.example.inazumaelevenexpress2.ui.screens.characters.CharacterDetailsScreen
import com.example.inazumaelevenexpress2.ui.screens.characters.InazumaCharactersUiState
import com.example.inazumaelevenexpress2.ui.screens.characters.InazumaCharactersViewModel
import com.example.inazumaelevenexpress2.ui.screens.main.MainScreen
import com.example.inazumaelevenexpress2.ui.screens.settings.SettingsScreen

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
                onNavigateToHome = {
                    navController.navigate(Screen.Main.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Main.route) {
            MainScreen(navController)
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen()
        }

        composable(route = Screen.Account.route) {
            AccountScreen()
        }

        composable(
            route = Screen.CharacterDetails.route,
            arguments = listOf(navArgument("characterId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: InazumaCharactersViewModel = viewModel(
                factory = InazumaCharactersViewModel.Factory
            )
            val characterId = backStackEntry.arguments?.getString("characterId")
            val character = (viewModel.inazumaCharactersUiState as? InazumaCharactersUiState.Success)?.characters?.find { it.characterId.toString() == characterId }
            if (character != null) {
                CharacterDetailsScreen(character = character, navController = navController)
            }
        }

        // ✅ FIXED: No ViewModel parameters - screen creates them internally
        composable(
            route = "assign-hissatsu/{characterId}/{characterName}",
            arguments = listOf(
                navArgument("characterId") { type = NavType.IntType },
                navArgument("characterName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            val characterName = backStackEntry.arguments?.getString("characterName") ?: "Character"

            // ✅ Screen creates its own ViewModels using factory pattern
            AssignHissatsuScreen(
                characterId = characterId,
                characterName = characterName,
                navController = navController
            )
        }
    }
}