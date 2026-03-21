package com.z3r08ug.vaxcareinterview.data.repository

import com.z3r08ug.vaxcareinterview.domain.repository.GreetingRepository
import javax.inject.Inject

class GreetingRepositoryImpl @Inject constructor() : GreetingRepository {
    override fun getGreeting(): String {
        return "Hello from Clean Architecture!"
    }
}
