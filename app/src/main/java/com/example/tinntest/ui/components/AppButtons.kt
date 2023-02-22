package com.example.tinntest.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tinntest.utils.emailIfValid

@Composable
fun AppButton(onClick: () -> Unit, enabled: Boolean, text: String) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        modifier = Modifier.width(150.dp),
        elevation = ButtonDefaults.elevation(16.dp)
    ) {
        Text(text)
    }
}