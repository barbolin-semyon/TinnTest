package com.example.tinntest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.tinntest.ui.navigation.AppNavHost
import com.example.tinntest.ui.navigation.Screens
import com.example.tinntest.ui.theme.TinnTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinnTestTheme {
                val token = LocalContext.current.getSharedPreferences(
                    "authorization", MODE_PRIVATE
                ).getString("token", "")

                val isConfirmEmail = LocalContext.current.getSharedPreferences(
                    "authorization", MODE_PRIVATE
                ).getBoolean("isConfirmEmail", false)

                val startDestination = if (token != "") {
                    Screens.SignIn.route
                } else {
                    if (isConfirmEmail) {
                        Screens.Main.route
                    } else {
                        Screens.ConfirmEmail.route
                    }
                }

                val navController = rememberNavController()
                AppNavHost(navController = navController, startDestination = startDestination)
            }
        }
    }
}
