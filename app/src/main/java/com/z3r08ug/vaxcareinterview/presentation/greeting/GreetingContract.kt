package com.z3r08ug.vaxcareinterview.presentation.greeting

import com.z3r08ug.vaxcareinterview.presentation.base.ViewEvent
import com.z3r08ug.vaxcareinterview.presentation.base.ViewSideEffect
import com.z3r08ug.vaxcareinterview.presentation.base.ViewState

object GreetingContract {
    sealed class Event : ViewEvent {
        data object OnButtonClicked : Event()
    }

    data class State(
        val name: String = "Android",
        val buttonClicked: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object ShowToast : Effect()
    }
}
