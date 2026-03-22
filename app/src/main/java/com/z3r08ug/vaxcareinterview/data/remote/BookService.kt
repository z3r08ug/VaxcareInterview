package com.z3r08ug.vaxcareinterview.data.remote

import com.z3r08ug.vaxcareinterview.data.model.BookDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

interface BookService {
    suspend fun getBooks(): List<BookDto>

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/android-test-vaxcare/27bd7ab7d0381f867723225145694e93/raw/c530190f575aaac1ab8d5c416b2da9553be422fe/local-database2.json"
    }
}

class BookServiceImpl @Inject constructor(
    private val client: HttpClient,
) : BookService {
    override suspend fun getBooks(): List<BookDto> {
        return client.get(BookService.BASE_URL).body()
    }
}
