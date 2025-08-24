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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.apachepoi.createExcelFile
import com.biryuchenko.designsystem.components.DeleteAlert
import com.biryuchenko.designsystem.components.MyFilledIconButton
import com.biryuchenko.designsystem.components.MyIconButton
import com.biryuchenko.documents.R
import com.biryuchenko.documents.menu.DocumentsViewModel
import com.biryuchenko.mlkit.Scanner
import com.biryuchenko.room.entities.Product
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.description_back)
                )
            }
            Text(
                text = stringResource(R.string.DocumentsScreenLabel),
            )
            IconButton(
                onClick = {
                    scope.launch {

                        createExcelFile(context, products = products.toList(), documentName)

                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = stringResource(R.string.description_back)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
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
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            isRotated.value = !isRotated.value
                            boxVisible.value = !boxVisible.value
                        },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
                            text = product.product.name
                        )
                        if (!boxVisible.value) {
                            Text(
                                modifier = Modifier
                                    .weight(0.3f)
                                    .padding(top = 10.dp, bottom = 10.dp),
                                text = product.product.price.toString()
                            )
                        }
                        MyIconButton(
                            modifier = Modifier.width(24.dp),
                            onClick = {
                                openDialog = true
                                onDelete = product.product
                            },
                            icon = Icons.Outlined.DeleteOutline,
                            contentDescription = stringResource(R.string.description_delete)
                        )
                        Icon(
                            modifier = Modifier
                                .graphicsLayer {
                                    rotationZ = rotationAngle
                                },
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.KeyboardArrowDown),
                        )
                    }
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        visible = boxVisible.value,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row {
                                    Text(
                                        modifier = Modifier.weight(0.5f),
                                        text = stringResource(R.string.output_price)
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
                                        text = stringResource(R.string.output_price_for_one)
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
                                        text = stringResource(R.string.count)
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
                                        text = stringResource(R.string.category)
                                    )
                                    Spacer(Modifier.width(20.dp))
                                    Text(
                                        modifier = Modifier.weight(0.4f),
                                        text = product.product.category
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.5.dp)
                        .padding(start = 15.dp, end = 15.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
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
                        text = stringResource(R.string.total)
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
                MyFilledIconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        scope.launch {
                            vm.result = scanner.startBarcodeScanSuspend(context)
                            if (vm.result != null) {
                                navigate(vm.result!!, documentId)
                            } else {
                                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    icon = Icons.Default.QrCodeScanner,
                    contentDescription = stringResource(R.string.barcode_scanner)
                )
                Spacer(Modifier.height(5.dp))
                MyFilledIconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = { navigate("", documentId) },
                    icon = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.description_edit)
                )
                Spacer(Modifier.height(67.dp))
            }

        }
        val isRotated = remember { mutableStateOf(false) }


        val rotationAngle by animateFloatAsState(
            targetValue = if (isRotated.value) 45f else 0f,
            animationSpec = tween(durationMillis = 300),
        )
        FilledIconButton(
            modifier = Modifier
                .size(48.dp),
            onClick = {
                wayToAdd = !wayToAdd
                isRotated.value = !isRotated.value
            },
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_document),
                modifier = Modifier.graphicsLayer {
                    rotationZ = rotationAngle
                })
        }
    }

    if (openDialog) {
        DeleteAlert(
            title = stringResource(R.string.delete_ask_product),
            text = stringResource(R.string.delete_ask_description_product),
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



