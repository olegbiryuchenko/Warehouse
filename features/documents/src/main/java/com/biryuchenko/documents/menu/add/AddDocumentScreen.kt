package com.biryuchenko.documents.menu.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun AddDocumentScreen(navigate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = navigate
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(30.dp))
                Text(
                    text = "Добавить документ",
                )
            }
            Spacer(Modifier.height(60.dp))
            Text(
                modifier = Modifier.width(290.dp),
                textAlign = TextAlign.Start,
                text = stringResource(R.string.inputDocumentName)
            )
            Spacer(Modifier.height(10.dp))
            TextField(
                modifier = Modifier.width(290.dp),
                placeholder = {
                    Text(
                        text = "Document"
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.inputDocumentName)
                    )
                },
                value = "",
                onValueChange = {}
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(0),
                colors = ButtonColors(
                    containerColor = colorResource(R.color.Green),
                    contentColor = Color.White,
                    disabledContentColor = colorResource(R.color.Green),
                    disabledContainerColor = Color.White,
                ),
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.add)
                )
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    AddDocumentScreen {  }
}
