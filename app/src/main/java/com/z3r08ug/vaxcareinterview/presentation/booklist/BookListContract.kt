package com.z3r08ug.vaxcareinterview.presentation.booklist

import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.presentation.base.ViewEvent
import com.z3r08ug.vaxcareinterview.presentation.base.ViewSideEffect
import com.z3r08ug.vaxcareinterview.presentation.base.ViewState

object BookListContract {
    sealed class Event : ViewEvent {
        data object LoadBooks : Event()
        data class OnBookClicked(val book: Book) : Event()
        data class OnFavoriteClicked(val book: Book) : Event()
        data object ToggleSortOrder : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val books: List<Book> = emptyList(),
        val error: String? = null,
        val sortOrder: SortOrder = SortOrder.ASCENDING,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class ShowError(val message: String) : Effect()
        data class NavigateToDetails(val bookId: Int) : Effect()
    }

    enum class SortOrder {
        ASCENDING,
        DESCENDING
    }
}
