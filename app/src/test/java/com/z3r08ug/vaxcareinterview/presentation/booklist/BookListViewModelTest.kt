package com.z3r08ug.vaxcareinterview.presentation.booklist

import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.model.BookStatus
import com.z3r08ug.vaxcareinterview.domain.usecase.GetBooksUseCase
import com.z3r08ug.vaxcareinterview.domain.usecase.RefreshBooksUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookListViewModelTest {

    @MockK
    lateinit var getBooksUseCase: GetBooksUseCase

    @MockK
    lateinit var refreshBooksUseCase: RefreshBooksUseCase

    private val testDispatcher = UnconfinedTestDispatcher()
    private val booksFlow = MutableStateFlow<List<Book>>(emptyList())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        
        every { getBooksUseCase() } returns booksFlow
        coEvery { refreshBooksUseCase() } coAnswers {
            // Simulate database update which triggers flow emission with a new list
            booksFlow.value = listOf(createBook(1))
            Result.success(Unit)
        }
    }

    private fun createBook(id: Int) = Book(
        id = id,
        title = "Title $id",
        author = "Author $id",
        status = BookStatus(1, "OnShelf"),
        fee = 1.0,
        lastEdited = ""
    )

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when loadBooks is called, isLoading is updated`() {
        val viewModel = BookListViewModel(getBooksUseCase, refreshBooksUseCase)
        
        // Initial call in init should have finished
        assertFalse(viewModel.viewState.value.isLoading)
    }

    @Test
    fun `when LoadBooks event is sent, refreshBooksUseCase is called`() {
        val viewModel = BookListViewModel(getBooksUseCase, refreshBooksUseCase)
        
        viewModel.setEvent(BookListContract.Event.LoadBooks)

        coVerify { refreshBooksUseCase() }
    }

    @Test
    fun `when refreshBooksUseCase fails, error state is updated and ShowError effect is produced`() = runTest {
        val errorMsg = "Network error"
        coEvery { refreshBooksUseCase() } returns Result.failure(Exception(errorMsg))
        
        val viewModel = BookListViewModel(getBooksUseCase, refreshBooksUseCase)
        
        assertEquals(errorMsg, viewModel.viewState.value.error)
        val effect = viewModel.effect.first()
        assert(effect is BookListContract.Effect.ShowError && effect.message == errorMsg)
    }

    @Test
    fun `when OnBookClicked event is sent, NavigateToDetails effect is produced`() = runTest {
        val book = createBook(1)
        val viewModel = BookListViewModel(getBooksUseCase, refreshBooksUseCase)
        
        viewModel.setEvent(BookListContract.Event.OnBookClicked(book))

        val effect = viewModel.effect.first()
        assert(effect is BookListContract.Effect.NavigateToDetails && effect.bookId == 1)
    }
}
