package com.example.inazumaelevenexpress2.ui.navigation

// Auth flow destinations (no bars/drawer)
sealed class AuthDestination(val route: String) {
    object Initial : AuthDestination("initial")
    object Login : AuthDestination("login")
    object Register : AuthDestination("register")

    companion object {
        fun fromRoute(route: String): AuthDestination = when (route) {
            Initial.route -> Initial
            Login.route -> Login
            Register.route -> Register
            else -> Initial
        }
    }
}

// Main app destinations (with full UI)
sealed class MainDestination(val route: String, val title: String) {
    object Main : MainDestination("main", "Main")
    object Home : MainDestination("home", "Home")
    object Characters : MainDestination("characters", "Characters")
    object Hissatsus : MainDestination("hissatsus", "Hissatsus")
    object Settings : MainDestination("settings", "Settings")
    object Account : MainDestination("account", "Account")


    companion object {
        fun fromRoute(route: String): MainDestination = when (route) {
            Main.route -> Main
            Home.route -> Home
            Characters.route -> Characters
            Hissatsus.route -> Hissatsus
            Settings.route -> Settings
            Account.route -> Account
            else -> Home
        }
    }
}

enum class Screen(val route: String) {
    Initial("initial"),
    Login("login"),
    Register("register"),
    Home("home"),
    Characters("characters"),
    Hissatsus("hissatsus"),
    Settings("settings"),
    Account("account"),
    Main("main"),
    CharacterDetails("character_details/{characterId}")
}
