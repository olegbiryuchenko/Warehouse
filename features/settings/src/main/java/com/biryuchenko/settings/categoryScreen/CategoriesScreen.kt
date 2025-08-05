package com.biryuchenko.settings.categoryScreen


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CategoriesScreen(
    navigateBack: () -> Unit,
    add: () -> Unit,

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
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
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
        LazyColumn(
            Modifier.fillMaxWidth()
        ) {
            items(3) {
                Button(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxSize(),
                    onClick = {}
                ) {

                }
                Spacer(Modifier.height(10.dp))
            }
        }

    }
    Box(
        modifier = Modifier
            .padding(bottom = 70.dp, end = 50.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        IconButton(
            modifier = Modifier.size(48.dp),
            colors = IconButtonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF06923E),
                disabledContentColor = Color.White,
                disabledContainerColor = Color(0xFF06923E),
            ),
            onClick = add
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add document")
        }
    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Previev() {
    CategoriesScreen(navigateBack = {}, add = {})
}