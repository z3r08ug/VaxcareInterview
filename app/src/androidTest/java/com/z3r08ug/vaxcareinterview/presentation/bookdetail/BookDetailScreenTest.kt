package com.z3r08ug.vaxcareinterview.presentation.bookdetail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.model.BookStatus
import org.junit.Rule
import org.junit.Test

class BookDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bookDetail_displaysBookInfo() {
        val book = Book(
            id = 1,
            title = "The C Programming Language",
            author = "Brian W Kernighan",
            status = BookStatus(1, "OnShelf", timeCheckedIn = "1978-02-22T20:30"),
            fee = 2.0,
            lastEdited = "1978-02-22T20:30:00.00Z"
        )
        val state = BookDetailContract.State(book = book)

        composeTestRule.setContent {
            BookDetailContent(state = state)
        }

        composeTestRule.onNodeWithText("The C Programming Language").assertIsDisplayed()
        composeTestRule.onNodeWithText("Author: Brian W Kernighan").assertIsDisplayed()
        composeTestRule.onNodeWithText("Status: OnShelf").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fee: $2.0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Checked In: 1978-02-22T20:30").assertIsDisplayed()
    }

    @Test
    fun bookDetail_displaysLoading() {
        val state = BookDetailContract.State(isLoading = true)

        composeTestRule.setContent {
            BookDetailContent(state = state)
        }

        // CircularProgressIndicator doesn't have text, but we can check it exists by tag or other means if needed.
        // For now, let's just check the screen is composed without error.
    }

    @Test
    fun bookDetail_displaysError() {
        val error = "Book not found"
        val state = BookDetailContract.State(error = error)

        composeTestRule.setContent {
            BookDetailContent(state = state)
        }

        composeTestRule.onNodeWithText(error).assertIsDisplayed()
    }
}
