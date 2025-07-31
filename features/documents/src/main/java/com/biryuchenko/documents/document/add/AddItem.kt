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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biryuchenko.documents.DocumentViewModel
import com.biryuchenko.documents.R
import kotlinx.coroutines.launch


@Composable
fun AddItemScreen(
    vm: DocumentViewModel,
    navigate: () -> Unit,
) {
    val scope = rememberCoroutineScope()
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
                    value = vm.result!!,
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
                    Text(stringResource(R.string.OutputPrice))
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        value = vm.outPrice,
                        label = { Text(stringResource(R.string.OutputPrice)) },
                        onValueChange = { txt ->
                            vm.outPrice = vm.filter(txt)
                            vm.calculatePricePerUnit()
                        }
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(stringResource(R.string.OutputPricePerItem))
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        value = vm.outPriceForOneItem,
                        label = { Text(stringResource(R.string.OutputPricePerItem)) },
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
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = stringResource(R.string.fullPrice)
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.fullPrice)
                        )
                    },
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
                Spacer(Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = stringResource(R.string.markup)
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.markup)
                        )
                    },
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
                Spacer(Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = stringResource(R.string.count)
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.count)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.inputCount)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = vm.count,
                    onValueChange = { txt ->
                        vm.count = vm.filter(txt)

                        scope.launch {
                            vm.calc()
                        }
                    }
                )
                Spacer(Modifier.height(10.dp))


            }
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
                onClick = navigate,
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
    AddItemScreen(navigate = {}, vm = viewModel())
}