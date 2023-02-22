package com.example.tinntest.ui.navigation

sealed class Screens(val route: String) {
    object SignIn : Screens("signIn")
    object Registration : Screens("registration")
    object ConfirmEmail : Screens("confirmEmail")
    object Main : Screens("main")
}
