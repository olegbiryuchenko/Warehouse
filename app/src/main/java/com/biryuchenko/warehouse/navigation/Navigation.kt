package com.biryuchenko.warehouse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.biryuchenko.database.DatabaseScreen
import com.biryuchenko.database.add.AddItemToDatabaseScreen
import com.biryuchenko.documents.document.DocumentScreen
import com.biryuchenko.documents.document.add.AddItemScreen
import com.biryuchenko.documents.document.add.AddItemScreenHandle
import com.biryuchenko.documents.menu.DocumentsScreen
import com.biryuchenko.documents.menu.add.AddDocumentScreen
import com.biryuchenko.home.HomeScreen
import com.biryuchenko.mlkit.Scanner
import com.biryuchenko.settings.SettingsScreen
import com.biryuchenko.settings.categoryScreen.CategoriesScreen
import com.biryuchenko.settings.categoryScreen.CategoryScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scanner = Scanner()
    NavHost(navController = navController, startDestination = HomeScreen) {
        composable<HomeScreen> {
            HomeScreen(
                navigateDocumentsScreen = { navController.navigate(DocumentsScreen) },
                navigateDatabaseScreen = { navController.navigate(Database) },
                navigateSettingsScreen = { navController.navigate(SettingsScreen) }
            )
        }
        composable<DocumentsScreen> {
            DocumentsScreen(
                addDocument = { navController.navigate(AddDocumentScreen) },
                navigate = { documentId, documentName ->
                    navController.navigate(DocumentScreen(documentId, documentName))
                },
                navigateBack = { navController.popBackStack() })
        }
        composable<DocumentScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<DocumentScreen>()
            DocumentScreen(
                scanner = scanner,
                navigate = { barcode, documentId ->
                    if (barcode.isEmpty()) {
                        navController.navigate(AddItemScreenHandle(documentId))
                    } else {
                        navController.navigate(AddItemScreen(barcode, documentId))
                    }
                },
                navigateBack = { navController.popBackStack() },
                documentId = args.documentId,
                documentName = args.documentName
            )
        }
        composable<AddItemScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AddItemScreen>()
            AddItemScreen(
                navigate = {
                    navController.popBackStack()
                },
                barcode = args.barcode,
                documentId = args.documentId
            )
        }
        composable<AddItemScreenHandle> { backStackEntry ->
            val args = backStackEntry.toRoute<AddItemScreenHandle>()
            AddItemScreenHandle(
                navigate = {
                    navController.popBackStack()
                },
                documentId = args.documentId
            )
        }
        composable<AddDocumentScreen> {
            AddDocumentScreen(navigate = { navController.popBackStack() })
        }

        composable<Database> {
            DatabaseScreen(
                navigate = { barcode ->
                    navController.navigate(AddItemToDatabaseScreen(barcode))
                },
                scanner = scanner,
                navigateBack = { navController.popBackStack() })

        }
        composable<AddItemToDatabaseScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AddItemToDatabaseScreen>()
            AddItemToDatabaseScreen(
                navigate = {
                    navController.popBackStack()
                },
                barcode = args.barcode
            )
        }
        composable<SettingsScreen> {
            SettingsScreen(navigateBack = { navController.popBackStack() }, navigateToCategories = {
                navController.navigate(
                    CategoriesScreen
                )
            }
            )
        }
        composable<CategoriesScreen> {
            CategoriesScreen(
                navigateBack = { navController.popBackStack() },
                add = { navController.navigate(CategoryScreen) })
        }
        composable<CategoryScreen> { CategoryScreen(navigateBack = { navController.popBackStack() }) }
    }
}

@Serializable
object HomeScreen

@Serializable
object DocumentsScreen

@Serializable
data class DocumentScreen(val documentId: Long, val documentName: String)

@Serializable
data class AddItemScreen(val barcode: String, val documentId: Long)

@Serializable
data class AddItemScreenHandle(val documentId: Long)

@Serializable
object AddDocumentScreen

@Serializable
object Database

@Serializable
data class AddItemToDatabaseScreen(val barcode: String)

@Serializable
object SettingsScreen

@Serializable
object CategoryScreen

@Serializable
object CategoriesScreen
