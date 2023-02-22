package com.example.tinntest.ui.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tinntest.ui.components.AppButton
import com.example.tinntest.ui.components.TextFieldEmail
import com.example.tinntest.ui.components.TextFieldPassword
import com.example.tinntest.ui.components.TextFieldsWithLabelError
import com.example.tinntest.utils.emailIfValid

@Composable
fun RegisterScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Авторизация", style = MaterialTheme.typography.h5)

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
            isError = password == repeatPassword && password.isNotEmpty(),
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
            onClick = { /*TODO*/ },
            enabled = email.emailIfValid() && password.length >= 8,
            text = "Продолжить"
        )
    }
}