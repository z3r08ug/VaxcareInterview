package com.z3r08ug.vaxcareinterview.presentation.booklist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.model.BookStatus
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class BookListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bookList_displaysBooks() {
        val books = listOf(
            Book(1, "Title 1", "Author 1", BookStatus(1, "OnShelf"), 1.0, ""),
            Book(2, "Title 2", "Author 2", BookStatus(1, "OnShelf"), 2.0, ""),
        )
        val state = BookListContract.State(books = books)

        composeTestRule.setContent {
            BookListContent(
                state = state,
                onRefresh = {},
            ) {}
        }

        composeTestRule.onNodeWithText("Title 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Author 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title 2").assertIsDisplayed()
    }

    @Test
    fun bookList_clickOnBook_triggersCallback() {
        val onBookClick: (Book) -> Unit = mockk(relaxed = true)
        val book = Book(1, "Title 1", "Author 1", BookStatus(1, "OnShelf"), 1.0, "")
        val state = BookListContract.State(books = listOf(book))

        composeTestRule.setContent {
            BookListContent(
                state = state,
                onRefresh = {},
                onBookClick = onBookClick
            )
        }

        composeTestRule.onNodeWithText("Title 1").performClick()

        verify { onBookClick(book) }
    }

    @Test
    fun bookList_displaysError() {
        val error = "Something went wrong"
        val state = BookListContract.State(error = error)

        composeTestRule.setContent {
            BookListContent(
                state = state,
                onRefresh = {},
            ) {}
        }

        composeTestRule.onNodeWithText(error).assertIsDisplayed()
    }
}
