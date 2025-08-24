package com.biryuchenko.documents.menu.folders

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.designsystem.components.DeleteAlert
import com.biryuchenko.documents.R
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.entities.Folder


@Composable
fun Folders(
    vm: FolderViewModel = hiltViewModel(),
    navigateBack: ()->Unit,
    navigateToDocument: (folderId: Long)->Unit,
    navigateToAdd: ()->Unit
){
    val context = LocalContext.current
    val folders by vm.allFolders.collectAsState()
    var openDialog by remember { mutableStateOf(false) }
    var folderToDelete by remember { mutableStateOf(Folder(0, "",  date = 0))}
    Column(
        Modifier.fillMaxSize(),
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
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.description_back)
                )
            }
            Text(
                text ="Папки",
            )
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(folders) { folder ->
                Button(
                    modifier = Modifier
                        .width(364.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(13),

                    onClick = {navigateToDocument(folder.uid) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.weight(0.1f),
                            imageVector = Icons.Outlined.Folder,
                            contentDescription = "folder"
                        )
                        Text(
                            modifier = Modifier.weight(0.7f),
                            text = folder.folderName,
                            textAlign = TextAlign.Start,
                        )

                        IconButton(
                            modifier = Modifier.weight(0.1f),
                            onClick = {
                                openDialog = true
                                folderToDelete = folder
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.description_delete)
                            )
                        }
                    }
                }
            }
            item {
                Button(
                    modifier = Modifier
                        .width(364.dp)
                        .height(70.dp),
                    shape = RoundedCornerShape(13),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.surfaceTint),
                    colors = ButtonColors(containerColor = Color.Transparent, disabledContainerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.onPrimary, disabledContentColor = MaterialTheme.colorScheme.onPrimary ),
                    onClick =  navigateToAdd
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.surfaceTint,
                        imageVector = Icons.Default.Add,
                        contentDescription = ""
                    )
                }
            }

        }
    }
    AnimatedVisibility(
        visible = openDialog
    ) {
        DeleteAlert(
            title = stringResource(R.string.delete_ask),
            text = stringResource(R.string.delete_ask_description) + folderToDelete.folderName,
            onDismissRequest = {
                openDialog = false
                Toast.makeText(context, "Отмена", Toast.LENGTH_SHORT).show()
            },
            onConfirm = {
                openDialog = false
                vm.delete(
                    Folder(
                        uid = folderToDelete.uid,
                        folderName = folderToDelete.folderName,
                        date = folderToDelete.date,

                    )
                )
            },
            onDismiss = { openDialog = false },
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun View(){
        Folders(navigateBack = {},navigateToDocument = {}, navigateToAdd = {})
}
