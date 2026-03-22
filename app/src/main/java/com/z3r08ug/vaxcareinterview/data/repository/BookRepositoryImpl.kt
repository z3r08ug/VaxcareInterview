package com.z3r08ug.vaxcareinterview.data.repository

import com.z3r08ug.vaxcareinterview.data.local.dao.BookDao
import com.z3r08ug.vaxcareinterview.data.local.entity.toDomain
import com.z3r08ug.vaxcareinterview.data.local.entity.toEntity
import com.z3r08ug.vaxcareinterview.data.remote.BookService
import com.z3r08ug.vaxcareinterview.data.model.toDomain
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookService: BookService,
    private val bookDao: BookDao,
) : BookRepository {

    override fun getBooks(): Flow<List<Book>> {
        return bookDao.getAllBooks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshBooks() {
        val response = bookService.getBooks()
        val networkBooks = response.map { it.toDomain() }
        
        // Preserve favorite status from current local data
        val currentBooks = bookDao.getAllBooks().first()
        val favoriteIds = currentBooks.filter { it.isFavorite }.map { it.id }.toSet()
        
        val updatedEntities = networkBooks.map { book ->
            book.copy(isFavorite = favoriteIds.contains(book.id)).toEntity()
        }
        
        bookDao.clearBooks()
        bookDao.insertBooks(updatedEntities)
    }

    override fun getBookById(id: Int): Flow<Book?> {
        return bookDao.getBookById(id).map { it?.toDomain() }
    }

    override suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        bookDao.updateFavoriteStatus(id, isFavorite)
    }
}
