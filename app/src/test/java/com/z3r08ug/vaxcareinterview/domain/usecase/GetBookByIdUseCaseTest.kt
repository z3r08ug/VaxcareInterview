package com.z3r08ug.vaxcareinterview.domain.usecase

import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetBookByIdUseCaseTest {

    @MockK
    lateinit var repository: BookRepository

    private lateinit var getBookByIdUseCase: GetBookByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getBookByIdUseCase = GetBookByIdUseCase(repository)
    }

    @Test
    fun `invoke should return book from repository when it exists`() = runTest {
        val bookId = 1
        val book = mockk<Book>()
        coEvery { repository.getBookById(bookId) } returns book

        val result = getBookByIdUseCase(bookId)

        assertEquals(book, result)
    }

    @Test
    fun `invoke should return null from repository when it does not exist`() = runTest {
        val bookId = 1
        coEvery { repository.getBookById(bookId) } returns null

        val result = getBookByIdUseCase(bookId)

        assertEquals(null, result)
    }
}
