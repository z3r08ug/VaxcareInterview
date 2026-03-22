package com.z3r08ug.vaxcareinterview.domain.usecase

import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import javax.inject.Inject

class RefreshBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        repository.refreshBooks()
    }
}
