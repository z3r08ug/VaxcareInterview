package com.z3r08ug.vaxcareinterview.presentation.greeting

import com.z3r08ug.vaxcareinterview.domain.usecase.GetGreetingUseCase
import com.z3r08ug.vaxcareinterview.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val getGreetingUseCase: GetGreetingUseCase
) : BaseViewModel<GreetingContract.Event, GreetingContract.State, GreetingContract.Effect>() {

    override fun setInitialState(): GreetingContract.State {
        return GreetingContract.State(name = getGreetingUseCase())
    }

    override fun handleEvents(event: GreetingContract.Event) {
        when (event) {
            is GreetingContract.Event.OnButtonClicked -> {
                setState { copy(buttonClicked = true) }
                setEffect { GreetingContract.Effect.ShowToast }
            }
        }
    }
}
