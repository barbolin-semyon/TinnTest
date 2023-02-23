package com.example.tinntest.ui.features

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tinntest.ui.components.AppButton
import com.example.tinntest.ui.components.CheckBoxWithLabel
import com.example.tinntest.ui.components.TextFieldEmail
import com.example.tinntest.ui.components.TextFieldPassword
import com.example.tinntest.ui.navigation.Screens
import com.example.tinntest.ui.theme.Gray
import com.example.tinntest.utils.AUTHORIZATION
import com.example.tinntest.utils.EMAIL_IS_CONFIRMATION
import com.example.tinntest.utils.TOKEN
import com.example.tinntest.utils.emailIfValid
import com.example.tinntest.viewModel.AuthorizationViewModel

@Composable
fun SignInScreen(navHostController: NavHostController) {
    val viewModel = viewModel(AuthorizationViewModel::class.java)

    val token by viewModel.token.observeAsState()
    if (token != "") {
        val context = LocalContext.current
        val pref = context.applicationContext.getSharedPreferences (
            AUTHORIZATION,
            Context.MODE_PRIVATE
        )
        pref.edit().putString(TOKEN, token).apply()
        pref.edit().putBoolean(EMAIL_IS_CONFIRMATION, true).apply()

        navHostController.navigate(Screens.Main.route) {
            popUpTo(navHostController.graph.startDestinationId) {
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
        Text(text = "Авторизация", style = MaterialTheme.typography.h5)

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isRememberUser by remember { mutableStateOf(false) }

        TextFieldEmail(email = email, onValueChange = { email = it })

        TextFieldPassword(
            password = password,
            onValueChange = { password = it },
            isError = password.length < 8 && password.isNotEmpty(),
            errorText = "Длина пароля не менее 8 символов",
            labelText = "Введите пароль"
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            CheckBoxWithLabel(checked = isRememberUser, onCheckedChanged = { isRememberUser = it })
        }
        AppButton(
            onClick = { viewModel.signIn(email, password) },
            enabled = email.emailIfValid() && password.length >= 8,
            text = "Войти"
        )

        TextButton(onClick = { navHostController.navigate(Screens.Registration.route) }) {
            Text("Нет аккаунта? Создайте его!")
        }
    }
}