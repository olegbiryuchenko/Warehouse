package com.biryuchenko.documents.menu.folders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.entities.Folder
import com.biryuchenko.room.repository.interfaces.DocumentRepository
import com.biryuchenko.room.repository.interfaces.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val folders: FolderRepository
) : ViewModel() {
    var txt by mutableStateOf("")
    val allFolders: StateFlow<List<Folder>> =
        folders.getAllItemsStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun add(){
        val currentDate = Date()
        val unixTimestampMillis: Long = currentDate.time
        viewModelScope.launch {
            folders.insertDocument(Folder(folderName = txt, date = unixTimestampMillis))
        }
    }

    fun delete(folder: Folder){
        viewModelScope.launch {
            folders.deleteItem(folder)
        }
    }
}