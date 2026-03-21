package com.z3r08ug.vaxcareinterview.presentation.greeting

import com.z3r08ug.vaxcareinterview.domain.usecase.GetGreetingUseCase
import com.z3r08ug.vaxcareinterview.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val getGreetingUseCase: GetGreetingUseCase
) : BaseViewModel<GreetingState, GreetingIntent, GreetingEffect>() {

    override fun createInitialState(): GreetingState {
        return GreetingState(name = getGreetingUseCase())
    }

    override fun handleIntent(intent: GreetingIntent) {
        when (intent) {
            is GreetingIntent.OnButtonClicked -> {
                setState { copy(buttonClicked = true) }
                setEffect { GreetingEffect.ShowToast }
            }
        }
    }
}
