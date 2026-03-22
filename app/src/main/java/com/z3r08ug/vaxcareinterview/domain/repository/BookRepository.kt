package com.z3r08ug.vaxcareinterview.domain.repository

import com.z3r08ug.vaxcareinterview.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getBooks(): Flow<List<Book>>
    suspend fun refreshBooks()
    suspend fun getBookById(id: Int): Book?
}
