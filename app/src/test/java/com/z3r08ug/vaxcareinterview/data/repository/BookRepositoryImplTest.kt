package com.z3r08ug.vaxcareinterview.data.repository

import com.z3r08ug.vaxcareinterview.data.local.dao.BookDao
import com.z3r08ug.vaxcareinterview.data.local.entity.BookEntity
import com.z3r08ug.vaxcareinterview.data.local.entity.BookStatusEntity
import com.z3r08ug.vaxcareinterview.data.local.entity.toDomain
import com.z3r08ug.vaxcareinterview.data.model.BookDto
import com.z3r08ug.vaxcareinterview.data.model.BookStatusDto
import com.z3r08ug.vaxcareinterview.data.remote.BookService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BookRepositoryImplTest {

    @MockK
    lateinit var bookService: BookService

    @MockK
    lateinit var bookDao: BookDao

    private lateinit var bookRepository: BookRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        bookRepository = BookRepositoryImpl(bookService, bookDao)
    }

    private fun createBookEntity(id: Int) = BookEntity(
        id = id,
        title = "Title $id",
        author = "Author $id",
        status = BookStatusEntity(1, "OnShelf", null, null, null),
        fee = 1.0,
        lastEdited = "",
        isFavorite = false,
    )

    private fun createBookDto(id: Int) = BookDto(
        id = id,
        title = "Title $id",
        author = "Author $id",
        status = BookStatusDto(1, "OnShelf", null, null, null),
        fee = 1.0,
        lastEdited = "",
    )

    @Test
    fun `getBooks should return mapped books from dao`() = runTest {
        val bookEntities = listOf(createBookEntity(1))
        val domainBooks = bookEntities.map { it.toDomain() }
        
        every { bookDao.getAllBooks() } returns flowOf(bookEntities)

        val result = bookRepository.getBooks().first()

        assertEquals(domainBooks, result)
    }

    @Test
    fun `refreshBooks should fetch from service and update dao`() = runTest {
        val bookDtos = listOf(createBookDto(1))
        
        every { bookDao.getAllBooks() } returns flowOf(emptyList())
        coEvery { bookService.getBooks() } returns bookDtos
        coEvery { bookDao.clearBooks() } returns Unit
        coEvery { bookDao.insertBooks(any()) } returns Unit

        bookRepository.refreshBooks()

        coVerify(exactly = 1) { bookService.getBooks() }
        coVerify(exactly = 1) { bookDao.clearBooks() }
        coVerify(exactly = 1) { bookDao.insertBooks(any()) }
    }

    @Test
    fun `getBookById should return mapped book from dao`() = runTest {
        val bookId = 1
        val bookEntity = createBookEntity(bookId)
        val domainBook = bookEntity.toDomain()
        
        every { bookDao.getBookById(bookId) } returns flowOf(bookEntity)

        val result = bookRepository.getBookById(bookId).first()

        assertEquals(domainBook, result)
    }

    @Test
    fun `getBookById should return null when dao returns null`() = runTest {
        val bookId = 1
        every { bookDao.getBookById(bookId) } returns flowOf(null)

        val result = bookRepository.getBookById(bookId).first()

        assertEquals(null, result)
    }

    @Test
    fun `toggleFavorite should call dao update`() = runTest {
        coEvery { bookDao.updateFavoriteStatus(1, true) } returns Unit

        bookRepository.toggleFavorite(1, true)

        coVerify(exactly = 1) { bookDao.updateFavoriteStatus(1, true) }
    }
}
