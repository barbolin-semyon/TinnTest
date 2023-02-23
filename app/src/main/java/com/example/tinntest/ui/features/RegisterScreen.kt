package com.example.tinntest.ui.features

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tinntest.ui.components.AppButton
import com.example.tinntest.ui.components.TextFieldEmail
import com.example.tinntest.ui.components.TextFieldPassword
import com.example.tinntest.ui.components.TextFieldsWithLabelError
import com.example.tinntest.ui.navigation.Screens
import com.example.tinntest.utils.AUTHORIZATION
import com.example.tinntest.utils.TOKEN
import com.example.tinntest.utils.emailIfValid
import com.example.tinntest.viewModel.AuthorizationViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel = viewModel(AuthorizationViewModel::class.java)

    val token by viewModel.token.observeAsState()
    if (token != "") {
        val context = LocalContext.current
        val pref = context.applicationContext.getSharedPreferences (
            AUTHORIZATION,
            Context.MODE_PRIVATE
        )
        pref.edit().putString(TOKEN, token).apply()

        navController.navigate(Screens.ConfirmEmail.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Регистрация", style = MaterialTheme.typography.h5)

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var repeatPassword by remember { mutableStateOf("") }
        var code by remember { mutableStateOf("") }

        TextFieldEmail(email = email, onValueChange = { email = it })

        TextFieldPassword(
            password = password,
            onValueChange = { password = it },
            isError = password.length < 8 && password.isNotEmpty(),
            errorText = "Длина пароля не менее 8 символов",
            labelText = "Введите пароль"
        )

        TextFieldPassword(
            password = repeatPassword,
            onValueChange = { repeatPassword = it },
            isError = password != repeatPassword && password.isNotEmpty(),
            errorText = "Пароли не совпадают",
            labelText = "Повторите пароль"
        )

        TextFieldsWithLabelError(
            value = code,
            onValueChange = { code = it },
            labelText = "Введите код"
        )

        AppButton(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { viewModel.register(email, password, code) },
            enabled = email.emailIfValid() && password.length >= 8 && password == repeatPassword,
            text = "Продолжить"
        )
    }
}