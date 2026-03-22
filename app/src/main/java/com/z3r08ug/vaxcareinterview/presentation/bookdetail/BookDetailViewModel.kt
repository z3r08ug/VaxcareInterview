package com.z3r08ug.vaxcareinterview.presentation.bookdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.z3r08ug.vaxcareinterview.domain.usecase.GetBookByIdUseCase
import com.z3r08ug.vaxcareinterview.presentation.base.BaseViewModel
import com.z3r08ug.vaxcareinterview.presentation.navigation.BookDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookByIdUseCase: GetBookByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<BookDetailContract.Event, BookDetailContract.State, BookDetailContract.Effect>() {

    private val bookId: Int = savedStateHandle.toRoute<BookDetailRoute>().id

    override fun setInitialState(): BookDetailContract.State {
        return BookDetailContract.State(isLoading = true)
    }

    init {
        loadBook(bookId)
    }

    override fun handleEvents(event: BookDetailContract.Event) {
        when (event) {
            is BookDetailContract.Event.LoadBook -> loadBook(event.bookId)
            is BookDetailContract.Event.OnBackClicked -> {
                setEffect { BookDetailContract.Effect.NavigateBack }
            }
        }
    }

    private fun loadBook(id: Int) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            val book = getBookByIdUseCase(id)
            if (book != null) {
                setState { copy(isLoading = false, book = book) }
            } else {
                setState { copy(isLoading = false, error = "Book not found") }
            }
        }
    }
}
