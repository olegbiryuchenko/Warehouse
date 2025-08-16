package com.biryuchenko.documents.document.add

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.documents.R
import com.biryuchenko.documents.document.DocumentDbVm
import kotlinx.coroutines.launch

@Composable
fun AddItemScreenHandle(
    vm: DocumentDbVm = hiltViewModel(),
    navigate: () -> Unit,
    documentId: Long,
) {

    val categories by vm.allCategories.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedCategoryId by remember { mutableStateOf<Long?>(null) }
    LaunchedEffect(key1 = documentId) {
        vm.setDocumentId(documentId)
    }


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
            Spacer(Modifier.height(20.dp))
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
                Column {
                    Text(
                        text = "Наименование",
                        fontSize = 10.sp,
                        color = Color.DarkGray
                    )
                    BasicTextField(
                        value = vm.name,
                        onValueChange = { txt ->
                            vm.name = txt
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black
                        ),
                    )
                }
            }

            Spacer(Modifier.height(35.dp))
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
                    .padding( start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
            ) {
                Spacer(Modifier.height(10.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(categories) { category ->

                        val isSelected = selectedCategoryId == category.uid

                        Spacer(Modifier.width(10.dp))
                        Button(
                            colors = ButtonColors(
                                containerColor = if (isSelected) Color(0xFF06923E) else Color.White,
                                contentColor = if (isSelected) Color.White else Color(0xFF06923E),
                                disabledContentColor = Color(0xFF06923E),
                                disabledContainerColor = Color.White
                            ), onClick = {
                                selectedCategoryId = category.uid
                                vm.categoryId = category.uid
                                vm.category = category.category
                                vm.percent = category.percent.toString()
                            }) {
                            Text(category.category)
                        }
                        Spacer(Modifier.width(10.dp))
                        Spacer(Modifier.height(20.dp))
                    }
                }
                Spacer(Modifier.height(10.dp))
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
                    value = vm.quantity,
                    onValueChange = { txt ->
                        vm.quantity = vm.filter(txt)

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
                }
            ) {
                Text(
                    text = stringResource(R.string.add)
                )
            }
        }
    }
}
