package com.z3r08ug.vaxcareinterview.presentation.booklist

import androidx.lifecycle.viewModelScope
import com.z3r08ug.vaxcareinterview.domain.usecase.GetBooksUseCase
import com.z3r08ug.vaxcareinterview.domain.usecase.RefreshBooksUseCase
import com.z3r08ug.vaxcareinterview.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val refreshBooksUseCase: RefreshBooksUseCase,
) : BaseViewModel<BookListContract.Event, BookListContract.State, BookListContract.Effect>() {

    override fun setInitialState(): BookListContract.State {
        return BookListContract.State(isLoading = true)
    }

    init {
        observeBooks()
        refreshBooks()
    }

    private fun observeBooks() {
        getBooksUseCase()
            .onEach { books ->
                setState { copy(books = books, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    override fun handleEvents(event: BookListContract.Event) {
        when (event) {
            is BookListContract.Event.LoadBooks -> refreshBooks()
            is BookListContract.Event.OnBookClicked -> {
                setEffect { BookListContract.Effect.NavigateToDetails(event.book.id) }
            }
        }
    }

    private fun refreshBooks() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            refreshBooksUseCase()
                .onFailure { error ->
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect { BookListContract.Effect.ShowError(error.message ?: "Unknown error") }
                }
        }
    }
}
