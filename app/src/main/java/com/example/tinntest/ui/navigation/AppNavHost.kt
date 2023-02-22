package com.example.tinntest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.SignIn.route) {

        }

        composable(Screens.Registration.route) {

        }

        composable(Screens.ConfirmEmail.route) {

        }

        composable(Screens.Main.route) {

        }
    }
}