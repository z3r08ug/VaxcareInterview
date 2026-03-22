package com.z3r08ug.vaxcareinterview.data.repository

import com.z3r08ug.vaxcareinterview.data.local.dao.BookDao
import com.z3r08ug.vaxcareinterview.data.local.entity.toDomain
import com.z3r08ug.vaxcareinterview.data.local.entity.toEntity
import com.z3r08ug.vaxcareinterview.data.remote.BookService
import com.z3r08ug.vaxcareinterview.data.model.toDomain
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
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
        val books = response.map { it.toDomain() }
        bookDao.clearBooks()
        bookDao.insertBooks(books.map { it.toEntity() })
    }

    override suspend fun getBookById(id: Int): Book? {
        return bookDao.getBookById(id)?.toDomain()
    }
}
