package com.example.tinntest.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(onClick: () -> Unit, enabled: Boolean, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = MaterialTheme.colors.primaryVariant
        ),
        modifier = modifier.width(150.dp),
        elevation = ButtonDefaults.elevation(16.dp)
    ) {
        Text(text, color = Color.White)
    }
}