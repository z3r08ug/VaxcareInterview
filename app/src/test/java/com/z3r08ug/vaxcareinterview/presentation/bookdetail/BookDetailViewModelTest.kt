package com.z3r08ug.vaxcareinterview.presentation.bookdetail

import androidx.lifecycle.SavedStateHandle
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.model.BookStatus
import com.z3r08ug.vaxcareinterview.domain.usecase.GetBookByIdUseCase
import com.z3r08ug.vaxcareinterview.presentation.navigation.BookDetailRoute
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import androidx.navigation.toRoute

@OptIn(ExperimentalCoroutinesApi::class)
class BookDetailViewModelTest {

    @MockK
    lateinit var getBookByIdUseCase: GetBookByIdUseCase

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<BookDetailRoute>() } returns BookDetailRoute(id = 1)
    }

    private fun createBook(id: Int) = Book(
        id = id,
        title = "Title $id",
        author = "Author $id",
        status = BookStatus(1, "OnShelf"),
        fee = 1.0,
        lastEdited = "",
    )

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is initialized, book is loaded`() = runTest {
        val book = createBook(1)
        coEvery { getBookByIdUseCase(1) } returns book

        val viewModel = BookDetailViewModel(getBookByIdUseCase, savedStateHandle)

        assertEquals(book, viewModel.viewState.value.book)
        assertFalse(viewModel.viewState.value.isLoading)
    }

    @Test
    fun `when book is not found, error state is updated`() = runTest {
        coEvery { getBookByIdUseCase(1) } returns null

        val viewModel = BookDetailViewModel(getBookByIdUseCase, savedStateHandle)

        assertEquals("Book not found", viewModel.viewState.value.error)
    }

    @Test
    fun `when OnBackClicked event is sent, NavigateBack effect is produced`() = runTest {
        coEvery { getBookByIdUseCase(1) } returns createBook(1)
        val viewModel = BookDetailViewModel(getBookByIdUseCase, savedStateHandle)

        viewModel.setEvent(BookDetailContract.Event.OnBackClicked)

        val effect = viewModel.effect.first()
        assertEquals(BookDetailContract.Effect.NavigateBack, effect)
    }
}
