package com.z3r08ug.vaxcareinterview.domain.usecase

import com.z3r08ug.vaxcareinterview.domain.repository.GreetingRepository
import javax.inject.Inject

class GetGreetingUseCase @Inject constructor(
    private val repository: GreetingRepository
) {
    operator fun invoke(): String {
        return repository.getGreeting()
    }
}
