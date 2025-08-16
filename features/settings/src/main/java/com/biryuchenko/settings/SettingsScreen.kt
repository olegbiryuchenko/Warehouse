package com.biryuchenko.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    navigateToCategories: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.description_back))
            }
            Text(
                text = stringResource(R.string.settings),
            )
        }
        Button(
            onClick = navigateToCategories
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = Modifier
                ) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        imageVector = Icons.Outlined.Category,
                        contentDescription = stringResource(R.string.categories)
                    )
                }
                Text(
                    stringResource(R.string.categories)
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