package com.example.tinntest.ui.features

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tinntest.ui.components.AppButton
import com.example.tinntest.ui.components.CheckBoxWithLabel
import com.example.tinntest.ui.components.TextFieldEmail
import com.example.tinntest.ui.components.TextFieldPassword
import com.example.tinntest.ui.navigation.Screens
import com.example.tinntest.ui.theme.Gray
import com.example.tinntest.utils.emailIfValid

@Composable
fun SignInScreen(navHostController: NavHostController) {
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
            onClick = { /*TODO*/ },
            enabled = email.emailIfValid() && password.length >= 8,
            text = "Войти"
        )

        TextButton(onClick = { navHostController.navigate(Screens.Registration.route) }) {
            Text("Нет аккаунта? Создайте его!")
        }
    }
}