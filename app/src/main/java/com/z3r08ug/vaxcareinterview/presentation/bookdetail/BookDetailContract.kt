package com.z3r08ug.vaxcareinterview.presentation.bookdetail

import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.presentation.base.ViewEvent
import com.z3r08ug.vaxcareinterview.presentation.base.ViewSideEffect
import com.z3r08ug.vaxcareinterview.presentation.base.ViewState

object BookDetailContract {
    sealed class Event : ViewEvent {
        data class LoadBook(val bookId: Int) : Event()
        data object OnBackClicked : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val error: String? = null,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateBack : Effect()
    }
}
