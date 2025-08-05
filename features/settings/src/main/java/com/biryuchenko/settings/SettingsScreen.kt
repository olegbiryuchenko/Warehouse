package com.biryuchenko.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    navigateToCategories: () -> Unit,
) {
    Column {
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(
                Modifier.width(30.dp),
            )
            Text(
                text = "Settings",
            )
        }
        Spacer(
            Modifier.height(30.dp),
        )
        Button(
            colors = ButtonColors(
                contentColor = Color.Unspecified,
                containerColor = Color.Transparent,
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Transparent,
            ),
            onClick = navigateToCategories
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color(0xFF107AB0), shape = RoundedCornerShape(24.dp)),
                ) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        imageVector = Icons.Outlined.Category,
                        contentDescription = "Категории"
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    "Категории"
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    SettingsScreen(navigateBack = {}, navigateToCategories = {})
}