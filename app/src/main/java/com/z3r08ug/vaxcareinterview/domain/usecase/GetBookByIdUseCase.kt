package com.z3r08ug.vaxcareinterview.domain.usecase

import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val repository: BookRepository,
) {
    operator fun invoke(id: Int): Flow<Book?> {
        return repository.getBookById(id)
    }
}
