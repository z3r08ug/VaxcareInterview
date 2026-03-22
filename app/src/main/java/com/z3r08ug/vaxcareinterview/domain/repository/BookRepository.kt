package com.z3r08ug.vaxcareinterview.domain.repository

import com.z3r08ug.vaxcareinterview.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getBooks(): Flow<List<Book>>
    suspend fun refreshBooks()
    fun getBookById(id: Int): Flow<Book?>
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
}
