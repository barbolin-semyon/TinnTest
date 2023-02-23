package com.example.tinntest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.tinntest.ui.navigation.AppNavHost
import com.example.tinntest.ui.navigation.Screens
import com.example.tinntest.ui.theme.TinnTestTheme
import com.example.tinntest.utils.AUTHORIZATION
import com.example.tinntest.utils.EMAIL_IS_CONFIRMATION
import com.example.tinntest.utils.TOKEN
import com.example.tinntest.viewModel.ErrorObserver
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinnTestTheme {
                val stateUI = rememberSystemUiController()
                stateUI.setStatusBarColor(MaterialTheme.colors.background)

                val token = LocalContext.current.getSharedPreferences(
                    AUTHORIZATION, MODE_PRIVATE
                ).getString(TOKEN, "")

                val isConfirmEmail = LocalContext.current.getSharedPreferences(
                    AUTHORIZATION, MODE_PRIVATE
                ).getBoolean(EMAIL_IS_CONFIRMATION, false)

                val startDestination = getStartDestination(token, isConfirmEmail)

                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                ObserverErrorMessage(snackbarHostState)

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    content = { padding ->
                        AppNavHost(
                            navController = navController,
                            startDestination = startDestination,
                            modifier = Modifier.padding(padding)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ObserverErrorMessage(snackBarState: SnackbarHostState) {
    val message by ErrorObserver.errorMessage.observeAsState()

    LaunchedEffect(key1 = message, block = {
        message?.let {
            if (it.isNotEmpty()) snackBarState.showSnackbar(it)
        }
    })
}

private inline fun getStartDestination(token: String?, isConfirmEmail: Boolean): String {
    return if (token == "") {
        Screens.SignIn.route
    } else {
        if (isConfirmEmail) {
            Screens.Main.route
        } else {
            Screens.ConfirmEmail.route
        }
    }
}