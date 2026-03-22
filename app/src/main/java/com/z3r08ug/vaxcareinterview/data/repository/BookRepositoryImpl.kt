package com.z3r08ug.vaxcareinterview.data.repository

import com.z3r08ug.vaxcareinterview.data.model.BookDto
import com.z3r08ug.vaxcareinterview.data.model.toDomain
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : BookRepository {

    override suspend fun getBooks(): List<Book> {
        val response: List<BookDto> = client.get("https://gist.githubusercontent.com/android-test-vaxcare/27bd7ab7d0381f867723225145694e93/raw/c530190f575aaac1ab8d5c416b2da9553be422fe/local-database2.json").body()
        return response.map { it.toDomain() }
    }
}
