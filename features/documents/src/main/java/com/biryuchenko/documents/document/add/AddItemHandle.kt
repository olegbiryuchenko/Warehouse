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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.designsystem.components.MyButton
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
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            //TODO REWRITE
            val maxLength = 30
            IconButton(
                onClick = navigate
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.description_back)
                )
            }
            Column {
                Text(
                    text = stringResource(R.string.name),
                    fontSize =  MaterialTheme.typography.labelSmall.fontSize,
                    color = MaterialTheme.colorScheme.outline
                )
                BasicTextField(
                    value = vm.name,
                    onValueChange = { txt ->
                        if (txt.length <= maxLength) {
                            vm.name = txt
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textStyle = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    ),
                )
            }
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
                    singleLine = true,
                    label = { Text(stringResource(R.string.OutputPrice)) },
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
                    singleLine = true,
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
                .padding(start = 30.dp, end = 30.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(categories) { category ->

                    val isSelected = selectedCategoryId == category.uid

                    Button(
                        onClick = {
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
