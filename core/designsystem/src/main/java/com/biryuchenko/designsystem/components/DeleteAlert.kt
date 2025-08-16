package com.biryuchenko.designsystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteAlert(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        title = {

            Text(text = title)
        },
        text = {

            Text(text = text)
        },
        confirmButton = {

            TextButton(
                onClick = onConfirm
            ) {

                Text(text = "Да")
            }
        },
        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Нет")
            }
        },
    )
}
