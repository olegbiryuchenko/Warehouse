package com.biryuchenko.documents.menu

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.biryuchenko.documents.R


@Composable
fun DocumentsScreen(navigate: () -> Unit, navigateBack: () -> Unit) {
    Column(
        Modifier.fillMaxSize()
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
            Spacer(Modifier.width(30.dp))
            Text(
                text = stringResource(R.string.DocumentsScreenLabel),
            )
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
        ) {
            items(4) {
                Button(
                    modifier = Modifier
                        .width(364.dp)
                        .height(50.dp),
                    colors = ButtonColors(
                        containerColor = colorResource(R.color.Green),
                        contentColor = Color.White,
                        disabledContentColor = colorResource(R.color.Green),
                        disabledContainerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(13),

                    onClick = navigate
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(0.8f),
                            text = "Placeholder limit 30 symbols ",
                            textAlign = TextAlign.Start,
                        )
                        IconButton(
                            modifier = Modifier.weight(0.1f),
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        }
                        IconButton(
                            modifier = Modifier.weight(0.1f),
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
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
                    containerColor = colorResource(R.color.Green),
                    disabledContentColor = Color.White,
                    disabledContainerColor =colorResource(R.color.Green),
                ),
                onClick = {}
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add document")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Pr() {
    DocumentsScreen(navigateBack={}, navigate = {})
}