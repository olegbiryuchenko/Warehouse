package com.biryuchenko.settings.categoryScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.room.entities.Category
import com.biryuchenko.settings.R


@Composable
fun CategoryScreen(
    navigateBack: () -> Unit,
    vm: CategoryViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
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
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(
                        R.string.description_back
                    )
                )
            }
            Text(
                text = stringResource(R.string.add_category),
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.input_name)
            )
            OutlinedTextField(
                value = vm.category,
                label = {
                    stringResource(R.string.input_name)
                },
                singleLine = true,
                placeholder = {
                    Text(stringResource(R.string.name))
                },
                onValueChange = {
                    vm.category = it
                }
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                stringResource(R.string.input_percent)
            )
            OutlinedTextField(
                value = vm.percent,
                label = {
                    stringResource(R.string.input_percent)
                },
                placeholder = {
                    Text(stringResource(R.string.percent))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                onValueChange = {
                    vm.percent = it
                }
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 45.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(0),
            onClick = {
                val percentValue = vm.percent.toIntOrNull()
                val name = vm.category
                if (percentValue != null && name.isNotBlank()) {
                    vm.insert(Category(category = name, percent = percentValue))
                    navigateBack()
                } else {
                    Toast.makeText(context, "Есть пустое поле", Toast.LENGTH_SHORT).show()
                }

            },
        ) {
            Text(
                text = stringResource(R.string.add)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    CategoryScreen(navigateBack = {})
}