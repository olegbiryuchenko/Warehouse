package com.biryuchenko.documents.document.add

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biryuchenko.designsystem.components.MyButton
import com.biryuchenko.documents.R
import com.biryuchenko.documents.document.DocumentDbVm
import kotlinx.coroutines.launch


@Composable
fun AddItemScreen(
    vm: DocumentDbVm = hiltViewModel(),
    navigate: () -> Unit,
    barcode: String,
    documentId: Long,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = documentId) {
        vm.setDocumentId(documentId)
        vm.findByBarcode(barcode, context)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            IconButton(
                onClick = navigate
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.description_back)
                )
            }
            Text(
                text = vm.name.ifEmpty { stringResource(R.string.not_found) },
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
        }
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(stringResource(R.string.OutputPrice))
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    value = vm.outPrice,
                    label = { Text(stringResource(R.string.OutputPrice)) },
                    singleLine = true,
                    onValueChange = { txt ->
                        vm.outPrice = vm.filter(txt)
                        vm.calculatePricePerUnit()
                    }
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(stringResource(R.string.OutputPricePerItem))
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    value = vm.outPriceForOneItem,
                    label = { Text(stringResource(R.string.OutputPricePerItem)) },
                    singleLine = true,
                    onValueChange = { txt ->
                        vm.outPriceForOneItem = vm.filter(txt)
                        vm.calculateTotalAmount()
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 50.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = stringResource(R.string.fullPrice)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.fullPrice)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(
                        text = stringResource(R.string.inputPrice)
                    )
                },
                value = vm.price,
                onValueChange = { txt ->
                    vm.price = vm.filter(txt)
                    scope.launch {
                        vm.calc()
                    }
                }
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = stringResource(R.string.markup)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.markup)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(
                        text = stringResource(R.string.inputMarkup)
                    )
                },
                value = vm.percent,
                onValueChange = { txt ->
                    vm.percent = vm.filter(txt)
                    scope.launch {
                        vm.calc()
                    }
                }
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = stringResource(R.string.count)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.count)
                    )
                },
                singleLine = true,
                label = {
                    Text(
                        text = stringResource(R.string.inputCount)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = vm.quantity,
                onValueChange = { txt ->
                    vm.quantity = vm.filter(txt)

                    scope.launch {
                        vm.calc()
                    }
                }
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        MyButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(0),
            onClick = {
                scope.launch {
                    val isSuccess = vm.add()
                    if (isSuccess) {
                        navigate()
                    } else {
                        Toast.makeText(
                            context,
                            "Ошибка при добавлении продукта",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
        ) {
            Text(
                text = stringResource(R.string.description_add)
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    AddItemScreen(navigate = {}, vm = viewModel(), barcode = "", documentId = 90)
}
