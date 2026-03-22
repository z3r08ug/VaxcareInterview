package com.z3r08ug.vaxcareinterview.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.z3r08ug.vaxcareinterview.presentation.booklist.BookListScreen
import kotlinx.serialization.Serializable

@Serializable
data object BookListRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BookListRoute
    ) {
        composable<BookListRoute> {
            BookListScreen(
                onBookClick = { bookId ->
                    // Handle navigation to details if needed
                }
            )
        }
    }
}
