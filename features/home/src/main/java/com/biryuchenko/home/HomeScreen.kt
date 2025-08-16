package com.biryuchenko.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.biryuchenko.designsystem.components.MyButton

@Composable
fun HomeScreen(
    navigateDocumentsScreen: () -> Unit,
    navigateDatabaseScreen: () -> Unit,
    navigateSettingsScreen: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 14.dp, end = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            MyButton(
                modifier = Modifier
                    .height(150.dp)
                    .weight(0.4f),
                shape = RoundedCornerShape(10),
                onClick = navigateDocumentsScreen
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.documents))
                }
            }
            MyButton(
                modifier = Modifier
                    .height(150.dp)
                    .weight(0.4f),
                shape = RoundedCornerShape(10),
                onClick = navigateDatabaseScreen
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.database))
                }
            }
        }
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            MyButton(
                modifier = Modifier
                    .height(150.dp)
                    .weight(0.4f),
                shape = RoundedCornerShape(10),
                onClick = navigateSettingsScreen
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.settings))
                }
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