package com.biryuchenko.warehouse.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.biryuchenko.database.DatabaseScreen
import com.biryuchenko.database.DatabaseVM
import com.biryuchenko.database.add.AddItemToDatabaseScreen
import com.biryuchenko.documents.DocumentViewModel
import com.biryuchenko.documents.document.DocumentScreen
import com.biryuchenko.documents.document.add.AddItemScreen
import com.biryuchenko.documents.menu.DocumentsScreen
import com.biryuchenko.documents.menu.add.AddDocumentScreen
import com.biryuchenko.home.HomeScreen
import com.biryuchenko.mlkit.Scanner
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scanner = Scanner()
    val databaseVm: DatabaseVM = viewModel()
    val documentVm: DocumentViewModel = viewModel()
    NavHost(navController = navController, startDestination = HomeScreen) {
        composable<HomeScreen> {
            HomeScreen(
                navigateDocumentsScreen = { navController.navigate(DocumentsScreen) },
                navigateDatabaseScreen = { navController.navigate(Database) },
            )
        }
        composable<DocumentsScreen> {
            DocumentsScreen(
                addDocument = { navController.navigate(AddDocumentScreen) },
                navigate = { navController.navigate(DocumentScreen) },
                navigateBack = { navController.popBackStack() })
        }
        composable<DocumentScreen> {
            DocumentScreen(
                scanner = scanner,
                navigate = {
                    navController.navigate(AddItemScreen)
                },
                navigateBack = { navController.popBackStack() })
        }
        composable<AddItemScreen> {
            AddItemScreen(
                vm = documentVm,
                navigate = {
                    navController.popBackStack()
                },
            )
        }
        composable<AddDocumentScreen> {
            AddDocumentScreen(navigate = { navController.popBackStack() })
        }

        composable<Database> {
            DatabaseScreen(
                navigate = {
                    navController.navigate(AddItemToDatabaseScreen)
                },
                vm = databaseVm,
                scanner = scanner,
                navigateBack = { navController.popBackStack() })

        }
        composable<AddItemToDatabaseScreen> {
            AddItemToDatabaseScreen(
                vm = databaseVm,
                navigate = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object DocumentsScreen

@Serializable
object DocumentScreen

@Serializable
object AddItemScreen

@Serializable
object AddDocumentScreen

@Serializable
object Database

@Serializable
object AddItemToDatabaseScreen