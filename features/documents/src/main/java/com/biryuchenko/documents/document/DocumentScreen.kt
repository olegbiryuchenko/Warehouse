package com.biryuchenko.documents.document

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.documents.R
import com.biryuchenko.mlkit.Scanner
import com.biryuchenko.room.entities.Product
import com.biryuchenko.ui.DeleteAlert
import kotlinx.coroutines.launch


@Composable
fun DocumentScreen(
    scanner: Scanner,
    navigate: (String, Long) -> Unit,
    navigateBack: () -> Unit,
    vm: DocumentDbVm = hiltViewModel(),
    documentId: Long,
    documentName: String,
) {
    LaunchedEffect(key1 = documentId) {
        vm.setDocumentId(documentId)
    }
    var onDelete: Product by remember { mutableStateOf(Product(0, ",", "", 0, 0, 0, 0)) }
    val products by vm.allProducts.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var wayToAdd by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    val totalPrice by vm.totalPrice.collectAsState()
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
                text = documentName,
            )
        }
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(products) { product ->
                val isRotated = remember { mutableStateOf(false) }


                val rotationAngle by animateFloatAsState(
                    targetValue = if (isRotated.value) 180f else 0f,
                    animationSpec = tween(durationMillis = 300),
                )

                val boxVisible = remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .width(364.dp)
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.Green),
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
                            text = product.product.name
                        )
                        Spacer(Modifier.width(10.dp))
                        if (!boxVisible.value) {
                            Text(
                                modifier = Modifier
                                    .weight(0.3f)
                                    .padding(top = 10.dp, bottom = 10.dp),
                                text = product.product.price.toString()
                            )
                        }
                        //TODO
                        IconButton(
                            onClick = {
                                openDialog = true
                                onDelete = product.product
                            }
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
                                Row {
                                    Text(
                                        modifier = Modifier.weight(0.5f),
                                        text = "Output Price:"
                                    )
                                    Spacer(Modifier.width(20.dp))
                                    Text(
                                        modifier = Modifier.weight(0.4f),
                                        text = product.product.price.toString()
                                    )
                                }
                                Spacer(Modifier.height(20.dp))
                                Row {
                                    Text(
                                        modifier = Modifier.weight(0.5f),
                                        text = "Output Price For One: "
                                    )
                                    Spacer(Modifier.width(20.dp))
                                    Text(
                                        modifier = Modifier.weight(0.4f),
                                        text = product.product.priceForOne.toString()
                                    )
                                }
                                Spacer(Modifier.height(20.dp))
                                Row {
                                    Text(
                                        modifier = Modifier.weight(0.5f),
                                        text = "Count: "
                                    )
                                    Spacer(Modifier.width(20.dp))
                                    Text(
                                        modifier = Modifier.weight(0.4f),
                                        text = product.product.quantity.toString()
                                    )
                                }
                                Spacer(Modifier.height(20.dp))
                                Row {
                                    Text(
                                        modifier = Modifier.weight(0.5f),
                                        text = "Category:"
                                    )
                                    Spacer(Modifier.width(20.dp))
                                    Text(
                                        modifier = Modifier.weight(0.4f),
                                        text = product.product.category
                                    )
                                }
                                Spacer(Modifier.height(20.dp))
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
                        .background(colorResource(R.color.Green))
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
                        text = totalPrice.toString()
                    )
                }
                Spacer(Modifier.height(35.dp))
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
            visible = wayToAdd,
        ) {
            Column {
                IconButton(
                    modifier = Modifier.size(48.dp), colors = IconButtonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xff464e5d),
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color(0xff464e5d),
                    ), onClick = {
                        scope.launch {
                            vm.result = scanner.startBarcodeScanSuspend(context)
                            if (vm.result != null) {
                                navigate(vm.result!!, documentId)
                            } else {
                                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                ) {
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
                    onClick = { navigate("", documentId) }


                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Add document")
                }
                Spacer(Modifier.height(67.dp))
            }

        }
        val isRotated = remember { mutableStateOf(false) }


        val rotationAngle by animateFloatAsState(
            targetValue = if (isRotated.value) 45f else 0f,
            animationSpec = tween(durationMillis = 300),
        )

        IconButton(
            modifier = Modifier.size(48.dp), colors = IconButtonColors(
                contentColor = Color.White,
                containerColor = colorResource(R.color.Green),
                disabledContentColor = Color.White,
                disabledContainerColor = colorResource(R.color.Green),
            ), onClick = {
                wayToAdd = !wayToAdd
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

    if (openDialog) {
        DeleteAlert(
            title = "Удалить документ",
            text = "Вы дейсвительно хотите удалить этот документ?",
            onDismissRequest = {
                openDialog = false
                Toast.makeText(context, "Отмена", Toast.LENGTH_SHORT).show()
            },
            onConfirm = {
                openDialog = false
                vm.delete(
                    onDelete,
                    context
                )
            },
            onDismiss = { openDialog = false },
        )
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
//    DocumentScreen(
//        navigate = {},
//        navigateBack = {},
//        scanner = Scanner(),
//        documentId = 0,
//        documentName = ""
//    )
}



