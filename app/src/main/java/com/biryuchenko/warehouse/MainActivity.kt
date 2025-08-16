package com.biryuchenko.warehouse


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.biryuchenko.designsystem.theme.WarehouseTheme
import com.biryuchenko.warehouse.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WarehouseTheme {
                Surface(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Navigation()
                }
            }
        }
    }
}

