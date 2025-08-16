package com.biryuchenko.settings.categoryScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biryuchenko.room.entities.Category
import com.biryuchenko.room.repository.interfaces.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    var category by mutableStateOf("")
    var percent by mutableStateOf("")

    val allCategories: StateFlow<List<Category>> =
        categoryRepository.getAllItemsStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun insert(category: Category) {
        viewModelScope.launch {
            try {
                categoryRepository.insertCategory(category)
            } catch (e: Exception) {
                println("Ошибка при вставке категории: ${e.message}")
            }
        }
    }

    fun delete(category: Category, context: Context) {
        viewModelScope.launch {
            try {
                categoryRepository.deleteItem(category)
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка при удалении: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}