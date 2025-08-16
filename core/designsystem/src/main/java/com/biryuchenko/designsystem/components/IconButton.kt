package com.biryuchenko.designsystem.components


import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MyFilledIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
) {

    FilledIconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun MyIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {

    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}