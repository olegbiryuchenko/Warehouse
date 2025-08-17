package com.biryuchenko.database.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biryuchenko.database.DatabaseVM
import com.biryuchenko.database.R
import com.biryuchenko.designsystem.components.MyButton

@Composable
fun AddItemToDatabaseScreen(
    navigate: () -> Unit,
    vm: DatabaseVM = hiltViewModel(),
    barcode: String,
) {
    val context = LocalContext.current
    val categories by vm.allCategories.collectAsState()
    var selectedCategoryId by remember { mutableStateOf<Long?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            IconButton(
                onClick = navigate
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = stringResource(R.string.add_to_db),
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            Spacer(Modifier.height(50.dp))
            Text(
                modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.barcode)
            )
            Spacer(Modifier.height(5.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = barcode,
                readOnly = true,
                singleLine = true,
                onValueChange = {})
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.name)
            )
            Spacer(Modifier.height(5.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(stringResource(R.string.input_name))
                }, value = vm.name, onValueChange = { txt ->
                    vm.name = txt
                })
            Spacer(Modifier.height(20.dp))
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                if (categories.isEmpty()) {
                    Text(
                        "Создайте категорию (меню настройки)"
                    )
                }
            }
            items(categories) { category ->

                val isSelected = selectedCategoryId == category.uid

                Spacer(Modifier.width(10.dp))
                Button(
                    onClick = {
                        selectedCategoryId = category.uid
                        vm.categoryId = category.uid
                        vm.category = category.category
                    }) {
                    Text(category.category)
                }
                Spacer(Modifier.width(10.dp))
                Spacer(Modifier.height(20.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 45.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            MyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    vm.add(barcode, context = context)
                    navigate()
                },
                shape = RoundedCornerShape(0),
            ) {
                Text(
                    text = stringResource(R.string.add_document)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    AddItemToDatabaseScreen(navigate = {}, vm = viewModel(), barcode = "ljp")
}