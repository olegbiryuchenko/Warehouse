package com.biryuchenko.documents.menu.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.designsystem.components.MyIconButton
import com.biryuchenko.documents.R
import com.biryuchenko.documents.menu.DocumentsViewModel

@Composable
fun AddDocumentScreen(
    navigate: () -> Unit,
    vm: DocumentsViewModel = hiltViewModel()
) {
    //TODO REWRITE
    val maxLength = 30
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            MyIconButton(
                modifier = Modifier.size(24.dp),
                onClick = navigate,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.description_back)
            )
            Text(
                text = stringResource(R.string.add_document),
            )
        }
        Text(
            textAlign = TextAlign.Start,
            text = stringResource(R.string.inputDocumentName)
        )
        OutlinedTextField(
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder_document_name)
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.inputDocumentName)
                )
            },
            singleLine = true,
            value = vm.text,
            supportingText = {
                Text(text = "${vm.text.length}/$maxLength")
            },
            onValueChange = { newText ->
                if (newText.length <= maxLength) {
                    vm.text = newText
                }
            }

        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(0),
            onClick = {
                vm.add()
                navigate()
            }
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
}
