package com.z3r08ug.vaxcareinterview.presentation.greeting

import com.z3r08ug.vaxcareinterview.presentation.base.ViewIntent
import com.z3r08ug.vaxcareinterview.presentation.base.ViewSideEffect
import com.z3r08ug.vaxcareinterview.presentation.base.ViewState

sealed class GreetingIntent : ViewIntent {
    object OnButtonClicked : GreetingIntent()
}

data class GreetingState(
    val name: String = "Android",
    val buttonClicked: Boolean = false
) : ViewState

sealed class GreetingEffect : ViewSideEffect {
    object ShowToast : GreetingEffect()
}
