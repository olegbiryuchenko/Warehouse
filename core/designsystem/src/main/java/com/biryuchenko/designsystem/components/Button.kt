package com.biryuchenko.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: RoundedCornerShape,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        onClick = onClick,

    ) {
        content()
    }
}