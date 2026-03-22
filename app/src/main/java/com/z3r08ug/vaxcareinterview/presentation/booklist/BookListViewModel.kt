package com.z3r08ug.vaxcareinterview.presentation.booklist

import androidx.lifecycle.viewModelScope
import com.z3r08ug.vaxcareinterview.domain.usecase.GetBooksUseCase
import com.z3r08ug.vaxcareinterview.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase
) : BaseViewModel<BookListContract.Event, BookListContract.State, BookListContract.Effect>() {

    override fun setInitialState(): BookListContract.State {
        return BookListContract.State(isLoading = true)
    }

    init {
        loadBooks()
    }

    override fun handleEvents(event: BookListContract.Event) {
        when (event) {
            is BookListContract.Event.LoadBooks -> loadBooks()
            is BookListContract.Event.OnBookClicked -> {
                setEffect { BookListContract.Effect.NavigateToDetails(event.book.id) }
            }
        }
    }

    private fun loadBooks() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            getBooksUseCase()
                .onSuccess { books ->
                    setState { copy(isLoading = false, books = books) }
                }
                .onFailure { error ->
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect { BookListContract.Effect.ShowError(error.message ?: "Unknown error") }
                }
        }
    }
}
