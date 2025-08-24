package com.biryuchenko.documents.menu.folders.add

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.documents.R
import com.biryuchenko.documents.menu.folders.FolderViewModel
import com.biryuchenko.room.entities.Folder

@Composable
fun CreateFolder(
    navigate: ()->Unit,
    vm: FolderViewModel = hiltViewModel()
){
    val context = LocalContext.current

    Column(
        Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
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
                text = "Создать папку",
            )
        }

        Text(
            text = "Название папки"
        )
        OutlinedTextField(
            label = {"Название папки"},
            value = vm.txt,
            onValueChange = {txt ->
                vm.txt = txt

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
                if (vm.txt.isNotBlank()) {
                    vm.add()
                    navigate()
                } else {
                    Toast.makeText(context, "Обнаружены пустые поля", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(
                text = stringResource(R.string.description_add)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CreateFolderView(){
    CreateFolder(navigate = {})
}