// ui/navigation/AppNavGraph.kt
package com.example.inazumaelevenexpress2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inazumaelevenexpress2.ui.screens.account.AccountScreen
import com.example.inazumaelevenexpress2.ui.screens.auth.InitialScreen
import com.example.inazumaelevenexpress2.ui.screens.auth.LoginScreen
import com.example.inazumaelevenexpress2.ui.screens.auth.RegisterScreen
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
                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                        // Pop up to the start destination of the graph to avoid building up a large stack of destinations
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
    }
}
