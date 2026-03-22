package com.z3r08ug.vaxcareinterview.domain.usecase

import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RefreshBooksUseCaseTest {

    @MockK
    lateinit var repository: BookRepository

    private lateinit var refreshBooksUseCase: RefreshBooksUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        refreshBooksUseCase = RefreshBooksUseCase(repository)
    }

    @Test
    fun `invoke should call refreshBooks on repository`() = runTest {
        coEvery { repository.refreshBooks() } returns Unit

        val result = refreshBooksUseCase()

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.refreshBooks() }
    }

    @Test
    fun `invoke should return failure when repository throws exception`() = runTest {
        val exception = Exception("Refresh failed")
        coEvery { repository.refreshBooks() } throws exception

        val result = refreshBooksUseCase()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
