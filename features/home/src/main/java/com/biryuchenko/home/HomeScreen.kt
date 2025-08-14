package com.biryuchenko.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    navigateDocumentsScreen: () -> Unit,
    navigateDatabaseScreen: () -> Unit,
    navigateSettingsScreen: () -> Unit,

    ) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = Color(0xFF121212))
            .fillMaxSize()
            .padding(top = 50.dp, start = 14.dp, end = 14.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .height(150.dp)
                    .weight(0.5f),
                colors = ButtonColors(
                    containerColor = Color(0xFF06923E),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFF06923E),
                    disabledContainerColor = Color.White
                ),
                shape = RoundedCornerShape(10),
                onClick = navigateDocumentsScreen
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text("Documents")
                }
            }
            Spacer(
                Modifier
                    .width(14.dp)
            )
            Button(
                modifier = Modifier
                    .height(150.dp)
                    .weight(0.4f), colors = ButtonColors(
                    containerColor = Color(0xFFE67514),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFFE67514),
                    disabledContainerColor = Color.White
                ),
                shape = RoundedCornerShape(10),
                onClick = navigateDatabaseScreen
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text("Database")
                }
            }
        }
        Spacer(
            Modifier
                .height(14.dp)
        )
        Row {
            Button(
                modifier = Modifier
                    .size(150.dp)
                    .weight(0.5f), colors = ButtonColors(
                    containerColor = Color(0xFFD3ECCD),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFFD3ECCD),
                    disabledContainerColor = Color.White
                ),
                shape = RoundedCornerShape(10), onClick = {
                    Toast.makeText(context,"not yet implemented", Toast.LENGTH_SHORT).show()
                }) {

            }
            Spacer(
                Modifier
                    .width(14.dp)
            )
            Button(
                modifier = Modifier
                    .size(150.dp)
                    .weight(0.5f), colors = ButtonColors(
                    containerColor = Color(0xFF4D9D98),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFF4D9D98),
                    disabledContainerColor = Color.White
                ),
                shape = RoundedCornerShape(10), onClick = navigateSettingsScreen
            ) {
                Text("Settings")
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    HomeScreen(
        navigateDocumentsScreen = {},
        navigateDatabaseScreen = {},
        navigateSettingsScreen = {})
}