package com.biryuchenko.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DatabaseVM : ViewModel() {
    var result by mutableStateOf<String?>("")
    var name by mutableStateOf("")
}