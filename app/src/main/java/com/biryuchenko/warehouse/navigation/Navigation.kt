package com.biryuchenko.warehouse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.biryuchenko.database.DatabaseScreen
import com.biryuchenko.database.add.AddItemToDatabaseScreen
import com.biryuchenko.documents.document.DocumentScreen
import com.biryuchenko.documents.document.add.AddItemScreen
import com.biryuchenko.documents.menu.DocumentsScreen
import com.biryuchenko.documents.menu.add.AddDocumentScreen
import com.biryuchenko.home.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeScreen) {
        composable<HomeScreen> {
            HomeScreen(
                navigateDocumentsScreen = { navController.navigate(DocumentsScreen) },
                navigateDatabaseScreen = { navController.navigate(Database) },
            )
        }
        composable<DocumentsScreen> {
            DocumentsScreen(
                navigate = { navController.navigate(DocumentScreen) },
                navigateBack = { navController.popBackStack() })
        }
        composable<DocumentScreen> {
            DocumentScreen(
                navigate = { navController.navigate(AddItemScreen) },
                navigateBack = { navController.popBackStack() })
        }
        composable<AddItemScreen> {
            AddItemScreen(
                navigate = {
                    navController.popBackStack()
                },
            )

        }
        composable<AddDocumentScreen> {
            AddDocumentScreen(navigate = { navController.navigate(DocumentScreen) })
        }

        composable<Database> {
            DatabaseScreen(
                navigate = { navController.navigate(AddItemToDatabaseScreen) },
                navigateBack = { navController.popBackStack() })

        }
        composable<AddItemToDatabaseScreen> { AddItemToDatabaseScreen(navigate = { navController.popBackStack()}) }
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