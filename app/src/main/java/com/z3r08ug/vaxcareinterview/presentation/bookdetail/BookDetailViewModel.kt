package com.z3r08ug.vaxcareinterview.presentation.bookdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.z3r08ug.vaxcareinterview.domain.usecase.GetBookByIdUseCase
import com.z3r08ug.vaxcareinterview.domain.usecase.ToggleFavoriteUseCase
import com.z3r08ug.vaxcareinterview.presentation.base.BaseViewModel
import com.z3r08ug.vaxcareinterview.presentation.navigation.BookDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.navigation.toRoute

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<BookDetailContract.Event, BookDetailContract.State, BookDetailContract.Effect>() {

    private val bookId: Int = savedStateHandle.toRoute<BookDetailRoute>().id

    override fun setInitialState(): BookDetailContract.State {
        return BookDetailContract.State(isLoading = true)
    }

    init {
        observeBook()
    }

    override fun handleEvents(event: BookDetailContract.Event) {
        when (event) {
            is BookDetailContract.Event.LoadBook -> { /* Handled by initial observation */ }
            is BookDetailContract.Event.OnBackClicked -> {
                setEffect { BookDetailContract.Effect.NavigateBack }
            }
            is BookDetailContract.Event.OnFavoriteClicked -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(event.book.id, !event.book.isFavorite)
                }
            }
        }
    }

    private fun observeBook() {
        getBookByIdUseCase(bookId)
            .onEach { book ->
                if (book != null) {
                    setState { copy(isLoading = false, book = book, error = null) }
                } else {
                    setState { copy(isLoading = false, error = "Book not found") }
                }
            }
            .launchIn(viewModelScope)
    }
}
