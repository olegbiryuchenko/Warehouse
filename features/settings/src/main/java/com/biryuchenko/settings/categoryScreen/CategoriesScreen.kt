package com.biryuchenko.settings.categoryScreen


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biryuchenko.designsystem.components.DeleteAlert
import com.biryuchenko.designsystem.components.MyFilledIconButton
import com.biryuchenko.room.entities.Category
import com.biryuchenko.settings.R


@Composable
fun CategoriesScreen(
    navigateBack: () -> Unit,
    add: () -> Unit,
    vm: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var openDialog by remember { mutableStateOf(false) }
    val categories by vm.allCategories.collectAsState()
    var onDelete: Category by remember { mutableStateOf(Category(0, ",", 0)) }
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
                onClick = { navigateBack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.description_back),
                )
            }
            Text(
                text = stringResource(R.string.categories),
            )
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(25.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            items(categories) { category ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = category.category,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.weight(0.7f))
                    IconButton(
                        onClick = {
                            openDialog = true
                            onDelete = category
                        },) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = stringResource(R.string.delete),
                        )
                    }
                }
            }
        }

    }
    Box(
        modifier = Modifier
            .padding(bottom = 70.dp, end = 50.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        MyFilledIconButton(
            modifier = Modifier.size(48.dp),
            onClick = { add() },
            icon = Icons.Default.Add,
            contentDescription = stringResource(R.string.add),
        )
    }
    if (openDialog) {
        DeleteAlert(
            title = stringResource(R.string.delete_ask_category),
            text = stringResource(R.string.delete_ask_description_category) + onDelete.category,
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
fun Previev() {
    CategoriesScreen(navigateBack = {}, add = {})
}