package com.example.tinntest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tinntest.ui.features.RegisterScreen
import com.example.tinntest.ui.features.SignInScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.SignIn.route) {
            SignInScreen(navController)
        }

        composable(Screens.Registration.route) {
            RegisterScreen(navController)
        }

        composable(Screens.ConfirmEmail.route) {

        }

        composable(Screens.Main.route) {

        }
    }
}