package com.biryuchenko.documents.document

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.biryuchenko.documents.R


@Composable
fun DocumentScreen(navigate: () -> Unit, navigateBack: () -> Unit) {
    val wayToAdd = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
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
            // TODO Document name MUST BE THIS
            Text(
                text = "Document name",
            )
        }
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(5) {
                val isRotated = remember { mutableStateOf(false) }

                // Анимируем угол поворота
                val rotationAngle by animateFloatAsState(
                    targetValue = if (isRotated.value) 180f else 0f, // 45 градусов, если isRotated = true, иначе 0
                    animationSpec = tween(durationMillis = 300), // Длительность анимации в миллисекундах (0.3 секунды)
                )

                val boxVisible = remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .width(364.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFF06923E),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            isRotated.value = !isRotated.value
                            boxVisible.value = !boxVisible.value
                        },


                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {


                        Text(
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
                            text = "Снежок мясное ассорти"
                        )
                        if (!boxVisible.value) {
                            Text(
                                modifier = Modifier
                                    .weight(0.3f)
                                    .padding(top = 10.dp, bottom = 10.dp),
                                text = "1200000"
                            )
                        }
                        //TODO
                        IconButton(
                            onClick = { openDialog.value = true }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DeleteOutline,
                                contentDescription = "Delete"
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "KeyboardArrowDown",
                            modifier = Modifier.graphicsLayer {
                                rotationZ = rotationAngle
                            }
                        )
                        Spacer(Modifier.width(15.dp))

                    }
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        visible = boxVisible.value,
                    ) {
                        Spacer(Modifier.height(20.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Output Price:"
                                )
                                Spacer(Modifier.height(15.dp))
                                Text(text = "Output Price For One: ")
                                Spacer(Modifier.height(15.dp))
                                Text(text = "Count: ")
                                Spacer(Modifier.height(15.dp))
                                Text(text = "Category:")
                                Spacer(Modifier.height(15.dp))
                            }
                            Column {
                                Text(
                                    text = "235 USD"
                                )
                                Spacer(Modifier.height(15.dp))
                                Text(text = " 1 USD")
                                Spacer(Modifier.height(15.dp))
                                Text(text = "235")
                                Spacer(Modifier.height(15.dp))
                                Text(text = "Feed")
                                Spacer(Modifier.height(15.dp))
                            }
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.2.dp)
                        .padding(start = 15.dp, end = 15.dp)
                        .background(Color(0xFF06923E))
                )
                Spacer(Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        fontSize = 18.sp,
                        text = "Игого: "
                    )
                    Text(
                        fontSize = 18.sp,
                        text = "125000"
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(bottom = 70.dp, end = 50.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        AnimatedVisibility(
            visible = wayToAdd.value,
        ) {
            Column {
                IconButton(
                    modifier = Modifier.size(48.dp), colors = IconButtonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xff464e5d),
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color(0xff464e5d),
                    ), onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.barcode_scanner_24px),
                        contentDescription = "Barcode Scanner",
                    )
                }
                Spacer(Modifier.height(5.dp))
                IconButton(
                    modifier = Modifier.size(48.dp), colors = IconButtonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xff464e5d),
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color(0xff464e5d),
                    ),
                    onClick = navigate


                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Add document")
                }
                Spacer(Modifier.height(67.dp))
            }

        }
        val isRotated = remember { mutableStateOf(false) }

        // Анимируем угол поворота
        val rotationAngle by animateFloatAsState(
            targetValue = if (isRotated.value) 45f else 0f, // 45 градусов, если isRotated = true, иначе 0
            animationSpec = tween(durationMillis = 300), // Длительность анимации в миллисекундах (0.3 секунды)
        )

        IconButton(
            modifier = Modifier.size(48.dp), colors = IconButtonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF06923E),
                disabledContentColor = Color.White,
                disabledContainerColor = Color(0xFF06923E),
            ), onClick = {
                wayToAdd.value = !wayToAdd.value
                isRotated.value = !isRotated.value
            }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add document",
                modifier = Modifier.graphicsLayer {
                    rotationZ = rotationAngle
                })
        }
    }
    //TODO
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = { Text(text = "Title") },
            text = {
                Text(
                    "This area typically contains the supportive text " +
                            "which presents the details regarding the Dialog's purpose."
                )
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) { Text("Dismiss") }
            },
        )
    }
    //TODO
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    DocumentScreen(navigate = {}, navigateBack = {})
}



