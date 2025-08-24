package com.biryuchenko.documents.menu


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.entities.FolderWithDocument
import com.biryuchenko.room.repository.interfaces.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DocumentsViewModel @Inject constructor(
    private val documents: DocumentRepository
) : ViewModel() {
    private val folderId = MutableStateFlow<Long>(0)
    var text by mutableStateOf("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val allDocuments: StateFlow<List<FolderWithDocument>> =
        folderId.flatMapLatest { id ->
            if (id == 0L) {

                flowOf(emptyList())
            } else {
                documents.get(id)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun setFolderId(id: Long) {
        folderId.value = id
    }
//    val allDocuments: StateFlow<List<Document>> =
//        documents.getAllItemsStream()
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = emptyList()
//            )

//    fun filter(firstDate: Long?, lastDate: Long?, cancel: Boolean) {
//        allDocuments = when {
//            cancel -> documents.getAllItemsStream()
//            firstDate != null && lastDate != null -> documents.filterByDate(firstDate, lastDate)
//            fiсrstDate != null && lastDate == null -> documents.filterByOneDate(firstDate)
//            else -> {
//                TODO()
//                documents.getAllItemsStream()
//            }
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = emptyList()
//        )
//    }
    fun add(context: Context) {
            val currentDate = Date()
            val unixTimestampMillis: Long = currentDate.time
            viewModelScope.launch {
                try {
                    documents.insertDocument(Document(document = text, date = unixTimestampMillis,folderId = folderId.value))
                } catch (e: Exception) {

                }
            }
    }

    fun delete(document: Document) {
        viewModelScope.launch {
            try {
                documents.deleteItem(document)
            } catch (e: Exception) {
                Log.e("DocumentsViewModel", "Error deleting document: ${e.message}", e)
            }
        }
    }
}