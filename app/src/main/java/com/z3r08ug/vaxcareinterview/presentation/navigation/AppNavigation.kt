package com.z3r08ug.vaxcareinterview.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.z3r08ug.vaxcareinterview.presentation.bookdetail.BookDetailScreen
import com.z3r08ug.vaxcareinterview.presentation.booklist.BookListScreen
import kotlinx.serialization.Serializable

@Serializable
data object BookListRoute

@Serializable
data class BookDetailRoute(val id: Int)

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
                    navController.navigate(BookDetailRoute(bookId))
                }
            )
        }
        composable<BookDetailRoute> {
            BookDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
