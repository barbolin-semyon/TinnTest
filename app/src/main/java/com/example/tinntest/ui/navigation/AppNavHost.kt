package com.example.tinntest.ui.navigation

import androidx.compose.runtime.Composable
 import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tinntest.ui.features.MainScreen
import com.example.tinntest.ui.features.RegisterScreen
import com.example.tinntest.ui.features.SignInScreen
import com.example.tinntest.ui.features.VerificationEmailScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screens.SignIn.route) {
            SignInScreen(navController)
        }

        composable(Screens.Registration.route) {
            RegisterScreen(navController)
        }

        composable(Screens.ConfirmEmail.route) {
            VerificationEmailScreen(navController)
        }

        composable(Screens.Main.route) {
            MainScreen()
        }
    }
}