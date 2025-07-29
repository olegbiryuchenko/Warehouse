package com.biryuchenko.documents.document.add

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AddItemScreen(navigate: () -> Unit) {
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
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Spacer(Modifier.width(30.dp))
                // TODO Document name MUST BE THIS
                BasicTextField(
                    value = "name item must be this",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    cursorBrush = SolidColor(Color.Green),

                    )
            }

            Spacer(Modifier.height(55.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 15.dp, end = 15.dp),
            ) {
                Column {
                    Text("Out Price:")
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = "",
                        label = { Text("Out Price") },
                        onValueChange = {}
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("Out Price:")
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = "",
                        label = { Text("Out Price for one item") },
                        onValueChange = {}
                    )
                }
            }
            Spacer(Modifier.height(60.dp))
            Text(
                modifier = Modifier.width(290.dp),
                textAlign = TextAlign.Start,
                text = "Цена:"
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.width(290.dp),
                placeholder = {
                    Text(
                        text = "Цена"
                    )
                },
                label = {
                    Text(
                        text = "Введите цену"
                    )
                },
                value = "",
                onValueChange = {}
            )
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.width(290.dp),
                textAlign = TextAlign.Start,
                text = "Наценка:"
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.width(290.dp),
                placeholder = {
                    Text(
                        text = "Наценка"
                    )
                },
                label = {
                    Text(
                        text = "Введите наценку"
                    )
                },
                value = "",
                onValueChange = {}
            )
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.width(290.dp),
                textAlign = TextAlign.Start,
                text = "Количество:"
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.width(290.dp),
                placeholder = {
                    Text(
                        text = "Количество"
                    )
                },
                label = {
                    Text(
                        text = "Введите Количество"
                    )
                },
                value = "",
                onValueChange = {}
            )
            Spacer(Modifier.height(10.dp))
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
                    containerColor = Color(0xFF06923E),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFF06923E),
                    disabledContainerColor = Color.White,
                ),
                onClick = navigate,
            ) {
                Text(
                    text = "Add"
                )
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    AddItemScreen(navigate = {})
}