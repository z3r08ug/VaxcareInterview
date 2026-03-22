package com.z3r08ug.vaxcareinterview.domain.usecase

import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: BookRepository,
) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) {
        repository.toggleFavorite(id, isFavorite)
    }
}
