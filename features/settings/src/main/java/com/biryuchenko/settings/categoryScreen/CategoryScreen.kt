package com.biryuchenko.settings.categoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CategoryScreen(
    navigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
        Column {
            Text(
                "Введите Имя:"
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = "",
                label = {
                    "Введите Имя:"
                },
                placeholder = {
                    Text("Имя")
                },
                onValueChange = {}
            )
        }
        Spacer(Modifier.height(30.dp))
        Column {
            Text(
                "Введите Процент(целое число):"
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = "",
                label = {
                    "Введите процент:"
                },
                placeholder = {
                    Text("Процент")
                },
                onValueChange = {}
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 15.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(0),
                colors = ButtonColors(
                    containerColor = Color(0xFF06923E),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFF06923E),
                    disabledContainerColor = Color.White,
                ),
                onClick = navigateBack,
            ) {
                Text(
                    text = "Добавить"
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    CategoryScreen(navigateBack = {})
}