package com.z3r08ug.vaxcareinterview.domain.usecase

import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetBooksUseCaseTest {

    @MockK
    lateinit var repository: BookRepository

    private lateinit var getBooksUseCase: GetBooksUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getBooksUseCase = GetBooksUseCase(repository)
    }

    @Test
    fun `invoke should return books from repository`() = runTest {
        val books = listOf(mockk<Book>())
        every { repository.getBooks() } returns flowOf(books)

        val result = getBooksUseCase().first()

        assertEquals(books, result)
    }
}
