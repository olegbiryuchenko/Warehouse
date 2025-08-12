package com.biryuchenko.documents.menu


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biryuchenko.room.entities.Document
import com.biryuchenko.room.repository.interfaces.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DocumentsViewModel @Inject constructor(
    private val documents: DocumentRepository
) : ViewModel() {
    var text by mutableStateOf("")
    val allDocuments: StateFlow<List<Document>> =
        documents.getAllItemsStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun add() {
        val currentDate = Date()
        val unixTimestampMillis: Long = currentDate.time
        viewModelScope.launch {
            try {
                documents.insertDocument(Document(document = text, date = unixTimestampMillis))
            } catch (e: Exception) {

            }
        }
    }

    fun delete(document: Document) {
        viewModelScope.launch {
            try {
                documents.deleteItem(document)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}